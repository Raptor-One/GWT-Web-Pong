package com.rptr1.pong.client;

import java.util.HashMap;
import java.util.Map;

public class GameCustomizations
{
    public static final String TARGET_FPS = "target_fps";
    public static final String BALL_SPEED_INCREASE = "ball_speed_increase";
    public static final String PADDLE_REFLECTION_INTENSITY = "paddle_reflection_intensity";
    public static final String PADDLE_MOVE_SPEED = "paddle_move_speed";
    public static final String PADDLE_WIDTH = "paddle_width";
    public static final String PADDLE_HEIGHT = "paddle_height";
    public static final String PADDLE_WALL_OFFSET = "paddle_wall_offset";
    public static final String BALL_SIZE = "ball_size";
    public static final String BALL_START_SPEED = "ball_start_speed";
    private static Map<String, Property<?>> properties = new HashMap<String, Property<?>>(){
        {
            put( TARGET_FPS, new Property<>( 100f ));
            put( BALL_SPEED_INCREASE, new Property<>( 4f ));
            put( PADDLE_REFLECTION_INTENSITY, new Property<>( 1f ));
            put( PADDLE_MOVE_SPEED, new Property<>( 60f ));
            put( PADDLE_WIDTH, new Property<>( 20f ));
            put( PADDLE_HEIGHT, new Property<>( 100f ));
            put( PADDLE_WALL_OFFSET, new Property<>( 70f ));
            put( BALL_SIZE, new Property<>( 30f ));
            put( BALL_START_SPEED, new Property<>( 45f ));
        }
    };

    public static <T> void setProperty( String key, T value )
    {
        Property property = properties.get( key );
        property.setValue( value );
    }

    public static <T> T getProperty( String key )
    {
        return (T) properties.get( key ).getValue();
    }

    public static class Property<T>
    {
        private T value;

        public Property( T value )
        {
            this.value = value;
        }


        public T getValue()
        {
            return value;
        }

        public void setValue( T value )
        {
            this.value = value;
        }
    }
}
