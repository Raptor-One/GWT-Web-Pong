package com.rptr1.pong.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.rptr1.pong.client.game.PongGame;
import com.rptr1.pong.client.ui.CustomPropertyController;
import com.rptr1.pong.client.ui.NumberSliderPropertyController;

import java.util.ArrayList;
import java.util.List;

import static com.rptr1.pong.client.GameCustomizations.*;

public class PongCustomizations
{
    public static void show(Runnable startGame)
    {
        FlowPanel container = new FlowPanel(  );
        List<CustomPropertyController> properties= new ArrayList<>(  );
        properties.add( new NumberSliderPropertyController( TARGET_FPS, "Target FPS", 10, 500, 0));
        properties.add( new NumberSliderPropertyController( BALL_START_SPEED, "Ball Start Speed", 5, 100, 0));
        properties.add( new NumberSliderPropertyController( BALL_SPEED_INCREASE, "Ball Speed Increase", -1, 10, 1));
        properties.add( new NumberSliderPropertyController( PADDLE_MOVE_SPEED, "Paddle Move Speed", 5, 100, 0));
        properties.add( new NumberSliderPropertyController( PADDLE_REFLECTION_INTENSITY, "Paddle Reflection Intensity", 0.5f, 1.5f, 2));
        properties.add( new NumberSliderPropertyController( BALL_SIZE, "Ball Size", 1, 150, 0));
        properties.add( new NumberSliderPropertyController( PADDLE_HEIGHT, "Paddle Height", 5, 300, 0));
        properties.add( new NumberSliderPropertyController( PADDLE_WIDTH, "Paddle Width", 1, 100    , 0));
        properties.add( new NumberSliderPropertyController( PADDLE_WALL_OFFSET, "Paddle Distance From Wall", 20, 200, 0));
        for( CustomPropertyController controller : properties )
            controller.generateUI( container );
        Button startButton = new Button(  );
        startButton.setText( "Play Game" );
        startButton.addClickHandler( event ->  {
            container.removeFromParent();
            startGame.run();
        });
        container.add( startButton );
        RootPanel.get().add( container );
    }
}
