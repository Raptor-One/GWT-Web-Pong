package com.rptr1.pong.client.game;

import com.google.gwt.user.client.Timer;
import com.rptr1.pong.client.GameCustomizations;

import java.util.logging.Logger;

import static com.rptr1.pong.client.GameCustomizations.*;
import static java.lang.System.currentTimeMillis;

public class PongGame
{
    private static final int WIDTH = 1600;
    private static final int HEIGHT = 900;
    private static final float PADDLE_OFFSET = getProperty( PADDLE_WALL_OFFSET );
    private static final int TARGET_FPS = (int)(float)getProperty( GameCustomizations.TARGET_FPS );
    private static final int TARGET_WAIT = 1000 / TARGET_FPS;
    private RenderCanvas canvas = new RenderCanvas( WIDTH, HEIGHT );
    private PongGameArena pongGameArena = new PongGameArena( WIDTH, HEIGHT, 5 );
    private ScoreBoard scoreBoard = new ScoreBoard( WIDTH );
    private Ball ball = new Ball( getProperty( BALL_SIZE ), getProperty( BALL_SIZE ), WIDTH / 2f, HEIGHT / 2f, getProperty( BALL_START_SPEED ), (float) Math.PI * 1 / 4 );
    private Paddle paddle1 = new Paddle( getProperty( PADDLE_WIDTH ), getProperty( PADDLE_HEIGHT ), PADDLE_OFFSET, HEIGHT / 2f );
    private Paddle paddle2 = new Paddle( getProperty( PADDLE_WIDTH ), getProperty( PADDLE_HEIGHT ), WIDTH - PADDLE_OFFSET, HEIGHT / 2f );
    private BallController ballController = new BallController( ball, paddle1, paddle2, pongGameArena.getYTopBound(), pongGameArena.getYBotBound(), getProperty( PADDLE_REFLECTION_INTENSITY ), getProperty( BALL_SPEED_INCREASE ) );
    private PaddleController paddle1Controller = new KeyboardPaddleController( paddle1, pongGameArena.getYTopBound(), pongGameArena.getYBotBound(), getProperty( PADDLE_MOVE_SPEED ), 87, 83 );
    private PaddleController paddle2Controller = new KeyboardPaddleController( paddle2, pongGameArena.getYTopBound(), pongGameArena.getYBotBound(), getProperty( PADDLE_MOVE_SPEED ), 38, 40 );
    private Timer timer;
    private long lastTime;

    Logger logger = Logger.getLogger( "PongGame" );

    public void init()
    {
        canvas.addObject( pongGameArena );
        canvas.addObject( scoreBoard );
        canvas.addObject( ball );
        canvas.addObject( paddle1 );
        canvas.addObject( paddle2 );
        run();
    }

    public void reset()
    {
        ball.setPosition( WIDTH / 2f, HEIGHT / 2f );
        ball.setSpeed( getProperty( BALL_START_SPEED ) );
        ball.setDirection( (float) Math.PI * 1 / 4 );
        paddle1.setPosition( PADDLE_OFFSET, HEIGHT / 2f );
        paddle2.setPosition( WIDTH - PADDLE_OFFSET, HEIGHT / 2f );
    }

    public void run()
    {
        lastTime = currentTimeMillis();
        timer = new Timer()
        {
            @Override
            public void run()
            {
                long delta = currentTimeMillis() - lastTime;
                lastTime = currentTimeMillis();
                ballController.calcNextFrame( delta );
                paddle1Controller.calcNextFrame( delta );
                paddle2Controller.calcNextFrame( delta );
                canvas.render();
                if( ball.getLeft() < pongGameArena.getXMinBound() || ball.getRight() > pongGameArena.getXMaxBound() )
                {
                    if( ball.getX() < WIDTH / 2 )
                        scoreBoard.incrementP2Score();
                    else
                        scoreBoard.incrementP1Score();
                    canvas.render();
                    reset();
                    runLater( () -> canvas.render(), 500 );
                    runLater( PongGame.this::run, 800 );
                    return;
                }
                int waitTime = TARGET_WAIT - (int) ( currentTimeMillis() - lastTime );
                if( waitTime < 0 )
                    logger.info( "Rendering took too long, skipping frames" );
                timer.schedule( Math.max( 0, TARGET_WAIT - (int) ( currentTimeMillis() - lastTime ) ) );
            }
        };
        timer.run();
    }

    private void runLater( Runnable action, int delayMillis )
    {
        Timer t = new Timer()
        {
            @Override
            public void run()
            {
                action.run();
            }
        };
        t.schedule( delayMillis );
    }
}
