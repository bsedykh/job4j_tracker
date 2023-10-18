package ru.job4j.oop;

public class Battery {
    private int load;

    public Battery(int load) {
        this.load = load;
    }

    public String about() {
        return "My charge: " + load + "%";
    }

    public void exchange(Battery another) {
        int charge = Math.min(load, 100 - another.load);
        this.load -= charge;
        another.load += charge;
    }

    public static void main(String[] args) {
        Battery first = new Battery(70);
        Battery second = new Battery(30);
        System.out.println("First." + first.about());
        System.out.println("Second." + second.about());
    }
}
