package com.rptr1.pong.client.game;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.user.client.ui.RootPanel;

public class KeyboardPaddleController extends PaddleController implements KineticObject
{
    private float moveSpeed;
    private int upKeyCode;
    private int downKeyCode;
    private boolean upKeyState = false;
    private boolean downKeyState = false;

    public KeyboardPaddleController( Paddle paddle, float yTopBound, float yBotBound, float moveSpeed, int upKeyCode, int downKeyCode )
    {
        super( paddle, yTopBound, yBotBound );
        this.moveSpeed = moveSpeed;
        this.upKeyCode = upKeyCode;
        this.downKeyCode = downKeyCode;
        RootPanel.get().addDomHandler( this::onKeyUp, KeyUpEvent.getType() );
        RootPanel.get().addDomHandler( this::onKeyDown, KeyDownEvent.getType() );
    }

    private void onKeyUp( KeyUpEvent event )
    {
        if( upKeyCode == event.getNativeKeyCode() )
            upKeyState = false;
        if( downKeyCode == event.getNativeKeyCode() )
            downKeyState = false;
    }

    private void onKeyDown( KeyDownEvent event )
    {
        if( upKeyCode == event.getNativeKeyCode() )
            upKeyState = true;
        if( downKeyCode == event.getNativeKeyCode() )
            downKeyState = true;
    }

    @Override
    public void calcNextFrame( long delta )
    {
        int deltaY = 0;
        if( upKeyState )
            deltaY -= moveSpeed;
        if( downKeyState )
            deltaY += moveSpeed;
        paddle.setY( Math.min( yBotBound - paddle.getHeight() / 2, Math.max( yTopBound + paddle.getHeight() / 2, paddle.getY() + deltaY * ( delta / 100f ) ) ) );
    }
}
