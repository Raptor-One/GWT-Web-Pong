package com.rptr1.pong.client.game;

import com.google.gwt.canvas.dom.client.Context2d;
import com.rptr1.pong.client.GameColors;

public class Ball extends GameObject
{
    private float speed;
    private float direction;

    public Ball( float width, float height, float x, float y, float speed, float direction )
    {
        super( width, height, x, y );
        this.speed = speed;
        this.direction = direction;
    }

    @Override
    public void render( Context2d context2d )
    {
        context2d.setFillStyle( GameColors.FOREGROUND_COLOR );
        context2d.fillRect( x - width / 2, y - height / 2, width, height );
        context2d.fill();
    }

    public float getSpeed()
    {
        return speed;
    }

    public float getDirection()
    {
        return direction;
    }

    public void setSpeed( float speed )
    {
        this.speed = speed;
    }

    public void setDirection( float direction )
    {
        this.direction = direction;
    }
}
