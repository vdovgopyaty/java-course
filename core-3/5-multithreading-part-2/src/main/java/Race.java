import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CyclicBarrier;

public class Race {

    private ArrayList<Stage> stages;
    private ArrayList<Car> participants;
    private HashMap<SignalType, Signal> signals;

    public Race(Stage[] stages, int participantQuantity) {
        Car[] cars = new Car[participantQuantity];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(20 + (int) (Math.random() * 10));
        }

        this.stages = new ArrayList<>(Arrays.asList(stages));
        this.participants = new ArrayList<>(Arrays.asList(cars));
        this.signals = new HashMap<>();

        signals.put(SignalType.START, new Signal("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!",
                participantQuantity));
        signals.put(SignalType.FINISH, new Signal("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!",
                participantQuantity));
    }

    public ArrayList<Stage> getStages() {
        return stages;
    }

    public CyclicBarrier getStartSignal() {
        return signals.get(SignalType.START).getBarrier();
    }

    public CyclicBarrier getFinishSignal() {
        return signals.get(SignalType.FINISH).getBarrier();
    }

    public void start() {
        for (Car participant : participants) {
            participant.addToRace(this);
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        for (Car participant : participants) {
            new Thread(participant).start();
        }
    }

    private class Signal {
        private CyclicBarrier barrier;

        private Signal(String message, int size) {
            this.barrier = new CyclicBarrier(size, () -> System.out.println(message));
        }

        public CyclicBarrier getBarrier() {
            return barrier;
        }
    }

    private enum SignalType {
        START, FINISH
    }
}
