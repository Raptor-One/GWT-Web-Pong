package com.rptr1.pong.client.game;

import java.util.logging.Logger;

public class BallController implements KineticObject
{
    private Ball ball;
    private Paddle paddleLeft;
    private Paddle paddleRight;
    private float yTopBound;
    private float yBotBound;
    private float paddleReflectionIntensity;
    private float ballSpeedIncrement;

    Logger logger = Logger.getLogger( "BallController" );

    public BallController( Ball ball, Paddle paddleLeft, Paddle paddleRight, float yTopBound, float yBotBound, float paddleReflectionIntensity, float ballSpeedIncrement )
    {
        this.ball = ball;
        this.paddleLeft = paddleLeft;
        this.paddleRight = paddleRight;
        this.yTopBound = yTopBound;
        this.yBotBound = yBotBound;
        this.paddleReflectionIntensity = paddleReflectionIntensity / 2;
        this.ballSpeedIncrement = ballSpeedIncrement;
    }

    public void calcNextFrame( long delta )
    {
        float timeScale = delta / 100f;
        float deltaX = (float) ( ball.getSpeed() * Math.cos( ball.getDirection() ) * timeScale );
        float deltaY = -(float) ( ball.getSpeed() * Math.sin( ball.getDirection() ) * timeScale );

        float upper = ( ball.getTop() + deltaY ) - yTopBound;
        float lower = yBotBound - ( ball.getBot() + deltaY );

        if( upper < 0 )
        {
            ball.y = yTopBound - upper + ball.getHeight() / 2;
            ball.setDirection( -ball.getDirection() );
        }
        else if( lower < 0 )
        {
            ball.y = yBotBound + lower - ball.getHeight() / 2;
            ball.setDirection( -ball.getDirection() );
        }
        else
        {
            ball.y += deltaY;
        }

        if( ball.getLeft() + deltaX < paddleLeft.getRight() && ball.getLeft() > paddleLeft.getLeft()
                && ball.getTop() < paddleLeft.getBot() && ball.getBot() > paddleLeft.getTop() )
        {
            ball.x = paddleLeft.getRight() * 2 - ball.x - deltaX + ball.width;
            ball.setDirection( (float) ( Math.PI * calculateReflectionFactor( paddleLeft ) ) );
            ball.setSpeed( ball.getSpeed() + ballSpeedIncrement );
//            ball.setDirection( (float) Math.PI - ball.getDirection() );
        }

        if( ball.getRight() + deltaX > paddleRight.getLeft() && ball.getRight() < paddleRight.getRight()
                && ball.getTop() < paddleRight.getBot() && ball.getBot() > paddleRight.getTop() )
        {
            ball.x = paddleRight.getLeft() * 2 - ball.x - deltaX - ball.width;
            ball.setDirection( (float) ( Math.PI + Math.PI * -calculateReflectionFactor( paddleRight )) );
            ball.setSpeed( ball.getSpeed() + ballSpeedIncrement );
//            ball.setDirection( (float) Math.PI - ball.getDirection() );
        }

        ball.x += deltaX;
    }

    private float calculateReflectionFactor( Paddle paddle )
    {
        return Math.max( -0.6f, Math.min( 0.6f, ( paddle.y - ball.y ) / paddle.height ) ) * paddleReflectionIntensity;
    }
}
