package com.coocaa.ie.games.wc2018.utils.web.ad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
import com.coocaa.ie.games.wc2018.penalty.actor.MyActor;

/**
 * Created by Sea on 2018/5/31.
 */

public class LoadImgFromNet {

    /**
     * 从网络中加载一张图片
     */
    public static void load(String url, final MyActor actor, final float width, final float height) {
        Gdx.app.debug("Sea-game", "load   ad-------"+url);
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url(url).build();
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                HttpStatus httpStatus = httpResponse.getStatus();
                if (httpStatus.getStatusCode() == 200) {
                    Gdx.app.debug("Sea-game", "请求图片成功");
                    final byte[] result = httpResponse.getResult();
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            Pixmap pixmap = new Pixmap(result, 0, result.length);
                            actor.setRegion(new TextureRegion(new Texture(pixmap)), width, height);
                            pixmap.dispose();
                        }
                    });

                } else {
                    Gdx.app.error("Sea-game", "请求图片失败: " + httpStatus.getStatusCode());
                }
            }

            @Override
            public void failed(Throwable throwable) {
                Gdx.app.error("Sea-game", "请求失败", throwable);
            }

            @Override
            public void cancelled() {
                Gdx.app.log("Sea-game", "请求被取消");
            }

        });

    }
}
