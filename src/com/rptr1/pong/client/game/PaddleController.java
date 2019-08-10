package com.rptr1.pong.client.game;

public abstract class PaddleController
{
    protected Paddle paddle;
    protected float yTopBound;
    protected float yBotBound;

    public PaddleController( Paddle paddle, float yTopBound, float yBotBound )
    {
        this.paddle = paddle;
        this.yTopBound = yTopBound;
        this.yBotBound = yBotBound;
    }

    public abstract void calcNextFrame( long delta );
}
