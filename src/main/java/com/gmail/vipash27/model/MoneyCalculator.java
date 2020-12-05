package com.gmail.vipash27.model;

public class MoneyCalculator {

    private final double PERCENT = 0.35;

    private double salaryPerShift; // заработная плата за смену
    private double balance; // сколько денег необходимо сдать в кассу

    private double totalSum;
    private double gas;
    private double belay; // страховка
    private double premium;
    private double carWash;
    private double cash;
    private double bonus;

    public double getSalaryPerShift() {
        return salaryPerShift;
    }

    // расчитать заработную плату за смену без чаевых и премии (сумма, которую тебе заплатит работодатель)
    public void setSalaryPerShift(double totalSum, double gas, double belay) {
        salaryPerShift = (totalSum - gas) * PERCENT - belay;
    }

    public double getBalance() {
        return balance;
    }

    // сумма, которую необходимо сдать в кассу в конце смены
    public void setBalance(double totalSum, double gas, double carWash, double cash, double bonus, double premium) {
        balance = totalSum - gas - carWash - cash - bonus - premium;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }

    public double getGas() {
        return gas;
    }

    public void setGas(double gas) {
        this.gas = gas;
    }

    public double getBelay() {
        return belay;
    }

    public void setBelay(double belay) {
        this.belay = belay;
    }

    public double getPremium() {
        return premium;
    }

    public void setPremium(double premium) {
        this.premium = premium;
    }

    public double getCarWash() {
        return carWash;
    }

    public void setCarWash(double carWash) {
        this.carWash = carWash;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
}
