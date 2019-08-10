package com.rptr1.pong.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusPanel;

import java.util.ArrayList;

public class SliderBar extends FocusPanel
{
    private ArrayList<ChangeHandler> changeHandlers = null;
    private double curValue;
    private Element knobElement;
    private Element lineElement;
    private double maxValue;
    private double minValue;
    private boolean slidingMouse = false;
    private double stepSize;

    public SliderBar()
    {
        super();

        // Create the outer shell
        getElement().setClassName( "slider" );

        // Create the line
        lineElement = DOM.createDiv();
        lineElement.addClassName( "slider-line" );
        DOM.appendChild( getElement(), lineElement );

        knobElement = DOM.createDiv();
        knobElement.addClassName( "slider-knob" );
        DOM.appendChild( getElement(), knobElement );

//        setSize( "100%", knobElement.getClientHeight() + "px" );

        sinkEvents( Event.MOUSEEVENTS | Event.FOCUSEVENTS );
    }

    public void addChangeHandler( ChangeHandler listener )
    {
        if( changeHandlers == null )
        {
            changeHandlers = new ArrayList<ChangeHandler>();
        }
        changeHandlers.add( listener );
    }

    public double getCurrentValue()
    {
        return curValue;
    }

    public double getMaxValue()
    {
        return maxValue;
    }

    public double getMinValue()
    {
        return minValue;
    }

    public double getStepSize()
    {
        return stepSize;
    }

    public double getTotalRange()
    {
        if( minValue > maxValue )
        {
            return 0;
        }
        else
        {
            return maxValue - minValue;
        }
    }

    @Override
    public void onBrowserEvent( Event event )
    {
        super.onBrowserEvent( event );
        switch( DOM.eventGetType( event ) )
        {
            // Unhighlight and cancel keyboard events
            case Event.ONBLUR:
                if( slidingMouse )
                {
                    DOM.releaseCapture( getElement() );
                    slidingMouse = false;
                    slideKnob( event );
                }
                break;

            // Highlight on focus
            case Event.ONFOCUS:
                highlight();
                break;

            // Mousewheel events
            case Event.ONMOUSEWHEEL:
                int velocityY = DOM.eventGetMouseWheelVelocityY( event );
                DOM.eventPreventDefault( event );
                if( velocityY > 0 )
                {
                    shiftRight( 1 );
                }
                else
                {
                    shiftLeft( 1 );
                }
                break;

            // Mouse Events
            case Event.ONMOUSEDOWN:
                setFocus( true );
                slidingMouse = true;
                DOM.setCapture( getElement() );
                DOM.eventPreventDefault( event );
                slideKnob( event );
                break;
            case Event.ONMOUSEUP:
                if( slidingMouse )
                {
                    DOM.releaseCapture( getElement() );
                    slidingMouse = false;
                    slideKnob( event );
                }
                break;
            case Event.ONMOUSEMOVE:
                if( slidingMouse )
                {
                    slideKnob( event );
                }
                break;
        }
    }

    public void onResize( int width, int height )
    {
        drawKnob();
    }

    /**
     * Redraw the progress bar when something changes the layout.
     */
    public void redraw()
    {
        if( isAttached() )
        {
            int width = DOM.getElementPropertyInt( getElement(), "clientWidth" );
            int height = DOM.getElementPropertyInt( getElement(),
                    "clientHeight" );
            onResize( width, height );
        }
    }

    /**
     * Remove a change listener from this SliderBar.
     *
     * @param listener the listener to remove
     */
    public void removeChangeHandler( ChangeHandler listener )
    {
        if( changeHandlers != null )
        {
            changeHandlers.remove( listener );
        }
    }

    /**
     * Set the current value and fire the onValueChange event.
     *
     * @param curValue the current value
     */
    public void setCurrentValue( double curValue )
    {
        setCurrentValue( curValue, true );
    }

    /**
     * Set the current value and optionally fire the onValueChange event.
     *
     * @param curValue  the current value
     * @param fireEvent fire the onValue change event if true
     */
    public void setCurrentValue( double curValue, boolean fireEvent )
    {
        // Confine the value to the range
        this.curValue = Math.max( minValue, Math.min( maxValue, curValue ) );
        double remainder = ( this.curValue - minValue ) % stepSize;
        this.curValue -= remainder;

        // Go to next step if more than halfway there
        if( ( remainder > ( stepSize / 2 ) )
                && ( ( this.curValue + stepSize ) <= maxValue ) )
        {
            this.curValue += stepSize;
        }

        // Redraw the knob
        drawKnob();

        // Fire the onValueChange event
        if( fireEvent && ( changeHandlers != null ) )
        {
            for( ChangeHandler changeHandler : changeHandlers )
            {
                changeHandler.onChange( null );
            }
        }
    }

    /**
     * Set the max value.
     *
     * @param maxValue the current value
     */
    public void setMaxValue( double maxValue )
    {
        this.maxValue = maxValue;
        resetCurrentValue();
    }

    /**
     * Set the minimum value.
     *
     * @param minValue the current value
     */
    public void setMinValue( double minValue )
    {
        this.minValue = minValue;
        resetCurrentValue();
    }

    /**
     * Set the step size.
     *
     * @param stepSize the current value
     */
    public void setStepSize( double stepSize )
    {
        this.stepSize = stepSize;
        resetCurrentValue();
    }

    /**
     * Shift to the left (smaller value).
     *
     * @param numSteps the number of steps to shift
     */
    public void shiftLeft( int numSteps )
    {
        setCurrentValue( getCurrentValue() - numSteps * stepSize );
    }

    /**
     * Shift to the right (greater value).
     *
     * @param numSteps the number of steps to shift
     */
    public void shiftRight( int numSteps )
    {
        setCurrentValue( getCurrentValue() + numSteps * stepSize );
    }

    /**
     * Get the percentage of the knob's position relative to the size of
     * the line.
     * The return value will be between 0.0 and 1.0.
     *
     * @return the current percent complete
     */
    protected double getKnobPercent()
    {
        // If we have no range
        if( maxValue <= minValue )
        {
            return 0;
        }

        // Calculate the relative progress
        double percent = ( curValue - minValue ) / ( maxValue - minValue );
        return Math.max( 0.0, Math.min( 1.0, percent ) );
    }

    /**
     * This method is called immediately after a widget becomes attached
     * to the
     * browser's document.
     */
    @Override
    protected void onLoad()
    {
        // Reset the position attribute of the parent element
        redraw();
    }

    @Override
    protected void onUnload()
    {
    }

    /**
     * Draw the knob where it is supposed to be relative to the line.
     */
    private void drawKnob()
    {
        // Abort if not attached
        if( !isAttached() )
        {
            return;
        }

        // Move the knob to the correct position
        int lineWidth = DOM.getElementPropertyInt( lineElement,
                "offsetWidth" );
        int knobWidth = DOM.getElementPropertyInt( knobElement,
                "offsetWidth" );
        int knobLeftOffset = (int) ( getKnobPercent() *
                ( lineWidth - knobWidth ) );
//        knobLeftOffset = Math.min( knobWidth / 2, lineWidth
//                - knobWidth  );
        DOM.setStyleAttribute( knobElement, "left", knobLeftOffset + "px" );
    }

    /**
     * Highlight this widget.
     */
    private void highlight()
    {
        String styleName = getStylePrimaryName();
        DOM.setElementProperty( getElement(), "className", styleName + " "
                + styleName + "-focused" );
    }

    /**
     * Reset the progress to constrain the progress to the current range
     * and
     * redraw the knob as needed.
     */
    private void resetCurrentValue()
    {
        setCurrentValue( getCurrentValue() );
    }

    /**
     * Slide the knob to a new location.
     *
     * @param event the mouse event
     */
    private void slideKnob( Event event )
    {
        int x = DOM.eventGetClientX( event );
        if( x > 0 )
        {
            int lineWidth = DOM.getElementPropertyInt( lineElement,
                    "offsetWidth" );
            int lineLeft = DOM.getAbsoluteLeft( lineElement );
            double percent = (double) ( x - lineLeft ) / lineWidth * 1.0;
            setCurrentValue( getTotalRange() * percent + minValue, true );
        }
    }

}