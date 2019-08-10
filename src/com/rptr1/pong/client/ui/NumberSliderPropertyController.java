package com.rptr1.pong.client.ui;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

import java.util.logging.Logger;

public class NumberSliderPropertyController extends CustomPropertyController<Float>
{
    private String friendlyName;
    private float min;
    private float max;
    private double factor;
    private FlowPanel container = new FlowPanel();
    private SliderBar sliderBar;
    private Label valueLabel;
    private NumberFormat numberFormat;

    Logger logger = Logger.getLogger( "NumberSliderPropertyController" );


    public NumberSliderPropertyController( String propertyKey, String friendlyName, float min, float max, int precision )
    {
        super(propertyKey);
        this.friendlyName = friendlyName + ": ";
        this.factor = Math.pow( 10, -precision);
        this.min = min;
        this.max = max;
        if( precision > 0)
        {
            StringBuilder patternBuilder = new StringBuilder( "0." );
            for( int i = Math.max( 0, precision ); i > 0; i-- )
            {
                patternBuilder.append(0);
            }
            numberFormat = NumberFormat.getFormat( patternBuilder.toString() );
        }
        else{
            numberFormat = NumberFormat.getFormat( "0" );
        }
    }

    @Override
    public void generateUI( Panel parent )
    {

        sliderBar = new SliderBar();
        sliderBar.setMinValue( min );
        sliderBar.setMaxValue( max );
        sliderBar.setStepSize( factor );
        sliderBar.setCurrentValue( (double) getPropertyValue() );
        sliderBar.addChangeHandler( event -> {
            setPropertyValue( (float)(double)sliderBar.getCurrentValue() );
            valueLabel.setText( friendlyName + numberFormat.format( getPropertyValue()));
        }
        );
        valueLabel = new Label(  );
        valueLabel.setText( friendlyName +  numberFormat.format( getPropertyValue() ) );
        container.setStyleName( "custom-control-box" );
        container.add( valueLabel );
        container.add( sliderBar );
        parent.add( container );
    }
}
