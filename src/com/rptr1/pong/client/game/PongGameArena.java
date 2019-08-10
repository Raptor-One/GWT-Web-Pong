package com.rptr1.pong.client.game;

import com.google.gwt.canvas.dom.client.Context2d;
import com.rptr1.pong.client.GameColors;

public class PongGameArena implements RenderableObject
{
    private final static int CENTER_LINE_LENGTH = 20;
    private final static int CENTER_LINE_WIDTH = 8;

    private int width;
    private int height;
    private int borderSize;

    public PongGameArena( int width, int height, int borderSize )
    {
        this.width = width;
        this.height = height;
        this.borderSize = borderSize;
    }

    @Override
    public void render( Context2d context2d )
    {
        context2d.setFillStyle( GameColors.FOREGROUND_COLOR );
        context2d.fillRect( 0, 0, width, height );
        context2d.setFillStyle( GameColors.BACKGROUND_COLOR );
        context2d.fillRect( borderSize, borderSize, width - borderSize * 2, height - borderSize * 2 );
        context2d.fill();

        context2d.beginPath();
        context2d.setStrokeStyle( GameColors.FOREGROUND_COLOR );
        context2d.setLineWidth( CENTER_LINE_WIDTH );
        for(int i = 0; i < height / CENTER_LINE_LENGTH; i+=2)
        {
            context2d.moveTo( width / 2f, i * CENTER_LINE_LENGTH );
            context2d.lineTo( width / 2f, ( i + 1 ) * CENTER_LINE_LENGTH );
        }
        context2d.stroke();
    }

    public float getYTopBound()
    {
        return borderSize;
    }
    public float getYBotBound()
    {
        return height - borderSize;
    }
    public float getXMinBound()
    {
        return borderSize;
    }
    public float getXMaxBound()
    {
        return width - borderSize;
    }

}
