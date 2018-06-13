package com.coocaa.ie.games.wc2018.answer.main.presenter.impl;

import com.coocaa.ie.core.system.vc.VoiceMonitor;
import com.coocaa.ie.core.system.vc.VoiceObject;
import com.coocaa.ie.core.utils.GameCountTimer;
import com.coocaa.ie.games.wc2018.WC2018Game;
import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.coocaa.ie.games.wc2018.answer.AnswerGame;
import com.coocaa.ie.games.wc2018.answer.main.model.AnswerMainModel;
import com.coocaa.ie.games.wc2018.answer.main.presenter.AnswerMainPresenter;
import com.coocaa.ie.games.wc2018.answer.main.stage.AnswerMainStage;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AnswerMainPresenterImpl implements AnswerMainPresenter {
    private WC2018Game mGame;
    private AnswerMainStage mStage;
    private AnswerMainModel mModel;
    private AnswerMainModel.Question question;

    private int coins = 0, score = 0;

    private GameCountTimer timer = new GameCountTimer();

    public AnswerMainPresenterImpl(WC2018Game game) {
        mGame = game;
    }

    @Override
    public void create(AnswerMainStage stage, AnswerMainModel model) {
        AnswerAudioManager.create(mGame);
        mStage = stage;
        mModel = model;
        mStage.setAnswerMainStageEventListener(eventListener);
        mStage.reset();
    }

    @Override
    public void onPause() {
        timer.pause();
        releaseFlashTipsTimer();
    }

    @Override
    public void onResume() {
        generalQuestion();
        timer.resume();
    }

    @Override
    public void playBgMusic() {
        AnswerAudioManager.audio().playBG();
    }

    @Override
    public void startNewGame() {
        mStage.reset();
        score = 0;
        coins = 0;
        extraVoice = 0;
        startGame();
    }

    @Override
    public void reloadGame() {
        mModel.reloadGame();
    }

    @Override
    public void continueGame() {
        mStage.reset(score, coins, AnswerGame.Rules.GAME_TIME_VALUE);
        startGame();
    }

    private void startGame() {
        mGame.getWC2018GameComponent().unRegisterVCMonitor(voiceMonitor);
        mGame.getWC2018GameComponent().registerVCMonitor(voiceMonitor);
        timer.start(AnswerGame.Rules.GAME_TIME_VALUE, new GameCountTimer.GameCountTimerListener() {
            @Override
            public void onTimeUpdate(int time) {
                mStage.updateTimer(time);
                if (time <= AnswerGame.Rules.TIME_URGENT_VALUE)
                    AnswerAudioManager.audio().playTimeUrgent();
                else {
                    AnswerAudioManager.audio().stopTimeUrgent();
                }
            }

            @Override
            public void onTimeOut() {
                timer.release();
                AnswerAudioManager.audio().stopTimeUrgent();
                AnswerAudioManager.audio().playTimeOut();
                endGame(AnswerMainStage.END_TYPE_TIME_OUT);
            }
        });
        generalQuestion();
    }

    @Override
    public void dispose() {
        mGame.getWC2018GameComponent().unRegisterVCMonitor(voiceMonitor);
        timer.release();
    }

    private Timer flashTipsTimer = null;

    private synchronized void newFlashTipsTimer(final Runnable runnable) {
        mStage.flashTips(false);
        releaseFlashTipsTimer();
        if (flashTipsTimer == null) {
            flashTipsTimer = new Timer();
            flashTipsTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runnable.run();
                    releaseFlashTipsTimer();
                }
            }, 10 * 1000);
        }
    }

    private synchronized void releaseFlashTipsTimer() {
        if (flashTipsTimer != null) {
            flashTipsTimer.cancel();
            flashTipsTimer = null;
        }
    }

    private void generalQuestion() {
        question = mModel.question();
        if (question != null) {
            mStage.initContent(question.description,
                    question.selections.get(0),
                    question.selections.get(1),
                    question.selections.get(2));
            newFlashTipsTimer(new Runnable() {
                @Override
                public void run() {
                    mStage.flashTips(true);
                }
            });
        }
    }

    private void endGame(int type) {
        releaseFlashTipsTimer();
        mStage.flashTips(false);
        mStage.showEnd(type);
        WC2018Game.WC2018GameComponent.Score _score = new WC2018Game.WC2018GameComponent.Score();
        _score.coins = coins;
        _score.score = score;
        mGame.getWC2018GameComponent().handleGameover(_score, new Runnable() {
            @Override
            public void run() {
                mStage.hideEnd();
            }
        });
    }

    private int extraVoice = 0;

    private final VoiceMonitor voiceMonitor = new VoiceMonitor() {
        @Override
        public void onReceive(VoiceObject obj) {
            if (obj != null && obj.command != null && obj.command.equals("28")) {
                extraVoice += 1;
                mGame.getWC2018GameComponent().updateLoggerExtra(extraVoice);
                showVCAnswer();
            }
        }
    };

    private void showVCAnswer() {
        mStage.hideVCTips();
        mGame.getWC2018GameComponent().showVCAnswer(question.id, question.description, question.selections.get(question.answer));
    }

    private final AnswerMainStage.AnswerMainStageEventListener eventListener = new AnswerMainStage.AnswerMainStageEventListener() {

        private Timer answerTimer = null;

        @Override
        public synchronized void onButtonClick(int button) {
            if (answerTimer == null && question != null) {
                final Runnable runnable;
                if (question.answer == button) {
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            generalQuestion();
                        }
                    };
                    score++;
                    boolean bDouble = score == AnswerGame.Rules.DOUBLE_GATE_VALUE;
                    if (bDouble) {
                        AnswerAudioManager.audio().playCritical();
                        try {
                            Map<String, String> params = new HashMap<>();
                            params.put("page_name", "游戏主界面");
                            params.put("status_name", "酷币翻倍");
                            WC2018GameController.getController().getGame().getWC2018GameComponent().logger("game_main_page_status_event", params);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else
                        AnswerAudioManager.audio().playCorrent();
                    coins = score * AnswerGame.Rules.COINS_VALUE;
                    if (score >= AnswerGame.Rules.DOUBLE_GATE_VALUE)
                        coins *= 2;
                    mStage.updateScore(coins, score, bDouble);
                    mStage.correctAnswer(button);
                    mStage.plusTimer(question.addTime);
                    timer.plusTime(question.addTime);
                } else {
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            AnswerAudioManager.audio().stopTimeUrgent();
                            AnswerAudioManager.audio().playGameOver();
                            endGame(AnswerMainStage.END_TYPE_GAME_OVER);
                            try {
                                Map<String, String> params = new HashMap<>();
                                params.put("page_name", "游戏主界面");
                                params.put("status_name", "时间到");
                                WC2018GameController.getController().getGame().getWC2018GameComponent().logger("game_main_page_status_event", params);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    AnswerAudioManager.audio().playIncorrent();
                    mStage.incorrectAnswer(button, question.answer);
                    timer.release();
                }
                answerTimer = new Timer();
                answerTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runnable.run();
                        answerTimer.cancel();
                        answerTimer = null;
                        mGame.getWC2018GameComponent().hideVCAnswer();
                    }
                }, 1000);
                mStage.hideDpadTips();
            }
        }

        @Override
        public void onDpadDownClick() {
            mGame.getWC2018GameComponent().showVCHelper();
        }

        @Override
        public void onBackKeyClick() {
//            mGame.getWC2018GameComponent().handleQuitGame();
        }

        @Override
        public void onDpadUpClick() {
            if (WC2018GameController.getController().isDebugMode())
                showVCAnswer();
        }

        @Override
        public void onDpadLeftClick() {
            mStage.hideDpadTips();
        }

        @Override
        public void onDpadRightClick() {
            mStage.hideDpadTips();
        }
    };
}
