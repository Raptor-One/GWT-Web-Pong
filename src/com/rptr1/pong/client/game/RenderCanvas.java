package com.rptr1.pong.client.game;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.ArrayList;
import java.util.List;

public class RenderCanvas
{
    private Canvas canvas;
    private Context2d context;
    private float ratio;
    private int width;
    private int height;

    private List<RenderableObject> objects = new ArrayList<>(  );

    public RenderCanvas( int internalWidth, int internalHeight )
    {
        width = internalWidth;
        height = internalHeight;
        ratio = (float) internalWidth / internalHeight;
        canvas = Canvas.createIfSupported();

        if( canvas == null )
        {
            RootPanel.get().add( new Label( "Sorry, your browser doesn't support the HTML5 RenderCanvas element" ) );
            return;
        }
        canvas.setCoordinateSpaceWidth( internalWidth );
        canvas.setCoordinateSpaceHeight( internalHeight );

        RootPanel.get().add( canvas );
        updateSize();
        Window.addResizeHandler( this::updateSize );

        context = canvas.getContext2d();

    }

    public void addObject(RenderableObject renderableObject)
    {
        objects.add( renderableObject );
    }

    public void render()
    {
        context.clearRect(0,0, width, height );
        for( RenderableObject renderableObject : objects )
        {
            renderableObject.render( context );
        }
    }

    private void updateSize( ResizeEvent resizeEvent )
    {
        System.out.println( "Window size adjusted to " + resizeEvent.getWidth() + "x" + resizeEvent.getHeight() );
        updateSize();
    }

    private void updateSize()
    {
        float screenRatio = (float) Window.getClientWidth() / Window.getClientHeight();

        if( screenRatio > ratio )
        {
            canvas.setHeight( Window.getClientHeight() + "px" );
            canvas.setWidth( Window.getClientHeight() * ratio + "px" );
            canvas.getElement().getStyle().setTop( 0, Style.Unit.PX  );
            canvas.getElement().getStyle().setLeft( (Window.getClientWidth() - Window.getClientHeight() * ratio) / 2, Style.Unit.PX  );

        }
        else
        {
            canvas.setHeight( Window.getClientWidth() / ratio + "px" );
            canvas.setWidth( Window.getClientWidth() + "px" );
            canvas.getElement().getStyle().setTop( (Window.getClientHeight() - Window.getClientWidth() / ratio) / 2, Style.Unit.PX  );
            canvas.getElement().getStyle().setLeft( 0, Style.Unit.PX  );
        }
    }
}
