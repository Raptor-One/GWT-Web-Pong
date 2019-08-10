package com.rptr1.pong.client.game;

import com.google.gwt.canvas.dom.client.Context2d;
import com.rptr1.pong.client.GameColors;

public class ScoreBoard implements RenderableObject
{

    private static final String PONG_SCORE_FONT = "120px PongScore";
    private static final int SCORE_SIDE_OFFSET = 300;
    private static final int SCORE_TOP_OFFSET = 200;

    private int areaWidth;
    private int p1Score = 0;
    private int p2Score = 0;

    public ScoreBoard( int areaWidth )
    {
        this.areaWidth = areaWidth;
    }

    @Override
    public void render( Context2d context2d )
    {
        context2d.setFillStyle( GameColors.FOREGROUND_COLOR );
        context2d.setFont( PONG_SCORE_FONT );
        double p1ScoreWidth = context2d.measureText( "" + p1Score ).getWidth();
        double p2ScoreWidth = context2d.measureText( "" + p2Score ).getWidth();
        context2d.fillText( "" + p1Score, SCORE_SIDE_OFFSET - p1ScoreWidth / 2, SCORE_TOP_OFFSET );
        context2d.fillText( "" + p2Score, areaWidth - SCORE_SIDE_OFFSET - p2ScoreWidth / 2, SCORE_TOP_OFFSET );
    }

    public void setP1Score( int p1Score )
    {
        this.p1Score = p1Score;
    }

    public void setP2Score( int p2Score )
    {
        this.p2Score = p2Score;
    }

    public void incrementP1Score()
    {
        this.p1Score++;
    }

    public void incrementP2Score()
    {
        this.p2Score++;
    }
}
