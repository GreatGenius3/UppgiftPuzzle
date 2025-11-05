import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimer
{
    private Timer timer;
    private TimerListener timerListener;
    private int seconds;
    private int minutes;
    private boolean isRunning;

    public interface TimerListener
    {
        void onTimeUpdated(String time);
        void onTimerStarted();
        void onTimerStopped();
    }

    GameTimer()
    {
        this.seconds = 0;
        this.minutes = 0;
        this.isRunning = false;

        timer = new Timer(1000, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tick();
            }
        });
    }

    public void setTimerListener(TimerListener listener)
    {
        this.timerListener = listener;
    }

    public void start()
    {
        if (!isRunning)
        {
            isRunning = true;
            timer.start();
            if (timerListener != null)
            {
                timerListener.onTimerStarted();
            }
        }
    }

    public void stop()
    {
        if (isRunning)
        {
            isRunning = false;
            timer.stop();
            if (timerListener != null)
            {
                timerListener.onTimerStopped();
            }
        }
    }

    public void reset()
    {
        stop();
        seconds = 0;
        minutes = 0;
        updateListener();
    }

    public String getTimeString()
    {
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void tick()
    {
        seconds++;
        if (seconds >= 60)
        {
            seconds = 0;
            minutes++;
        }
        updateListener();
    }

    private void updateListener()
    {
        if (timerListener != null)
        {
            timerListener.onTimeUpdated(getTimeString());
        }
    }

    public boolean isRunning()
    {
        return isRunning;
    }
}
