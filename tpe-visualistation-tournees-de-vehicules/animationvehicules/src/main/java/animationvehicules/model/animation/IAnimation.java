package animationvehicules.model.animation;

public interface IAnimation {

    public void dataChecking(int time);

    Car createCar(int numCar, int indexSource, int indexDestination);

    public Car updateCarPosition(int numCar, double percentageCompletion);

    public void updateCarRoute(int numCar, int indexSource, int indexDestination);

}
