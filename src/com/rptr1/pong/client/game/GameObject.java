package com.rptr1.pong.client.game;

public abstract class GameObject implements RenderableObject
{
    protected float width;
    protected float height;
    protected float x;
    protected float y;

    public GameObject( float width, float height, float x, float y )
    {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public void setPosition( float x, float y )
    {
        this.x = x;
        this.y = y;
    }

    public float getWidth()
    {
        return width;
    }

    public void setWidth( float width )
    {
        this.width = width;
    }

    public float getHeight()
    {
        return height;
    }

    public void setHeight( float height )
    {
        this.height = height;
    }

    public float getX()
    {
        return x;
    }

    public void setX( float x )
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY( float y )
    {
        this.y = y;
    }

    public float getTop()
    {
        return y - height / 2;
    }

    public float getBot()
    {
        return y + height / 2;
    }

    public float getLeft()
    {
        return x - width / 2;
    }

    public float getRight()
    {
        return x + width / 2;
    }
}
