import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Klassen GameTimer
// Denna är en timer som används för att
// räkna ut antal sekunder och minuter
public class GameTimer
{
    private Timer timer;
    private TimerListener timerListener;
    private int seconds;
    private int minutes;
    private boolean isRunning;

    // Vi skapar ett interface TimerListener
    // Denna används för att signalera när timern startar, stoppas eller uppdateras
    public interface TimerListener
    {
        void onTimeUpdated(String time);
        void onTimerStarted();
        void onTimerStopped();
    }

    // Konstruktor
    GameTimer()
    {
        this.seconds = 0;
        this.minutes = 0;
        this.isRunning = false;

        // Vi skapar en timer och anger en ActionListener
        // som anropar funktionen tick() för varje 1000 millisekunder
        timer = new Timer(1000, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tick();
            }
        });
    }

    // Denna funktion sätter en TimerListener
    public void setTimerListener(TimerListener listener) { this.timerListener = listener; }

    // Starta timern
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

    // Stoppa timern
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

    // Återställ timern
    public void reset()
    {
        stop();
        seconds = 0;
        minutes = 0;
        updateListener();
    }

    // Returnerar tiden i formatet mm:ss
    public String getTimeString()
    {
        return String.format("%02d:%02d", minutes, seconds);
    }

    // Funktionen tick
    // Denna anropas vid vald tid
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

    // Uppdaterar timerListener
    private void updateListener()
    {
        if (timerListener != null)
        {
            timerListener.onTimeUpdated(getTimeString());
        }
    }

    // Returnerar true om timern är igång
    public boolean isRunning()
    {
        return isRunning;
    }
}
