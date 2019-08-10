package com.rptr1.pong.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.rptr1.pong.client.game.PongGame;
import com.rptr1.pong.client.ui.NumberSliderPropertyController;

public class PongApp implements EntryPoint
{
    private PongGame pongGame;

    public void onModuleLoad()
    {
       PongCustomizations.show(() -> {
           if(pongGame == null)
           {
               pongGame = new PongGame();
               pongGame.init();
           }
       });
    }

}
