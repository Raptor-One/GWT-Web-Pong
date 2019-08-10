package com.rptr1.pong.client.game;

import com.google.gwt.canvas.dom.client.Context2d;
import com.rptr1.pong.client.GameColors;

public class Paddle extends GameObject
{
    public Paddle( float width, float height, float x, float y )
    {
        super( width, height, x, y );
    }

    @Override
    public void render( Context2d context2d )
    {
        context2d.setFillStyle( GameColors.FOREGROUND_COLOR );
        context2d.fillRect( x - width / 2, y - height / 2, width, height );
        context2d.fill();
    }
}
