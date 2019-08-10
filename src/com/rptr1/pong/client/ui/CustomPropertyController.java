package com.rptr1.pong.client.ui;

import com.google.gwt.user.client.ui.Panel;
import com.rptr1.pong.client.GameCustomizations;

public abstract class CustomPropertyController<T>
{
    private String propertyKey;

    public CustomPropertyController( String propertyKey )
    {
        this.propertyKey = propertyKey;
    }

    public abstract void generateUI( Panel parent );

    protected void setPropertyValue( T value )
    {
        GameCustomizations.setProperty( propertyKey, value );
    }

    protected T getPropertyValue()
    {
        return GameCustomizations.getProperty( propertyKey );
    }
}
