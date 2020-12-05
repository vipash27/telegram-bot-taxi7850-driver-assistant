package com.gmail.vipash27.service;

import com.gmail.vipash27.dao.UserDao;
import com.gmail.vipash27.dao.impl.UserDaoImpl;
import com.gmail.vipash27.model.MoneyCalculator;
import com.gmail.vipash27.model.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private final String BOT_USER_NAME = "7850DA1.0";
    private final String BOT_TOKEN = "1227425296:AAG_FbjKOjCk2GzzWVWgLzvnitl_9zsLo8s";

    private final String TOTAL_SUM = "Общая сумму за смену:";
    private final String GAS = "Заправка:";
    private final String CAR_WASH = "Мойка:";
    private final String BALANCE = "/balance";
    private final String SALARY = "/salary";
    private final String EXIT = "/exit";
    private String start = "start";
    private String line = "";

    private MoneyCalculator calculator = new MoneyCalculator();

    @Override
    public String getBotUsername() {
        return BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        if (update.getMessage().hasText()) {
            if (message.equals("/start")) {
                addUser(update);
            } else if (message.equals(EXIT)) {
                resetOperation(chatId);
            } else if (update.getMessage().hasText()) {
                if (message.equals(BALANCE) || line.equals(BALANCE)) {
                    getBalance(message, chatId);
                } else if (message.equals(SALARY) || line.equals(SALARY)) {
                    getSalary(message, chatId);
                } else {
                    resetOperation(chatId);
                }
            }
        }
    }

    private synchronized void sendMessage(String message, long chatId) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void getBalance(String message, long chatId) {
        line = "/balance";
        if (start.equals("start")) {
            start = "totalSum";
            sendMessage(TOTAL_SUM, chatId);
        } else if (start.equals("totalSum")) {
            double totalSum = Double.parseDouble(message);
            calculator.setTotalSum(totalSum);
            start = "gas";
            sendMessage(GAS, chatId);
        } else if (start.equals("gas")) {
            double gas = Double.parseDouble(message);
            calculator.setGas(gas);
            start = "carWash";
            sendMessage(CAR_WASH, chatId);
        } else if (start.equals("carWash")) {
            double carWash = Double.parseDouble(message);
            calculator.setCarWash(carWash);
            start = "cash";
            sendMessage("Безнал:", chatId);
        } else if (start.equals("cash")) {
            double cash = Double.parseDouble(message);
            calculator.setCash(cash);
            start = "bonus";
            sendMessage("Бонус:", chatId);
        } else if (start.equals("bonus")) {
            double bonus = Double.parseDouble(message);
            calculator.setBonus(bonus);
            start = "premium";
            sendMessage("Премия:", chatId);
        } else if (start.equals("premium")) {
            double premium = Double.parseDouble(message);
            calculator.setPremium(premium);
            double totalSum = calculator.getTotalSum();
            double gas = calculator.getGas();
            double carWash = calculator.getCarWash();
            double cash = calculator.getCash();
            double bonus = calculator.getBonus();
            calculator.setBalance(totalSum, gas, carWash, cash, bonus, premium);
            sendMessage("ИТОГО отдать в кассу: " + calculator.getBalance(), chatId);
            calculator = new MoneyCalculator();
            line = "";
            start = "start";
            sendMessage("/save", chatId);
        }
    }

    private void getSalary(String message, long chatId) {
        line = "/salary";
        if (start.equals("start")) {
            start = "totalSum";
            sendMessage(TOTAL_SUM, chatId);
        } else if (start.equals("totalSum")) {
            double totalSum = Double.parseDouble(message);
            calculator.setTotalSum(totalSum);
            start = "gas";
            sendMessage(GAS, chatId);
        } else if (start.equals("gas")) {
            double gas = Double.parseDouble(message);
            calculator.setGas(gas);
            start = "belay";
            sendMessage("КАСКО (будний день - 3 р.; выходной день - 5 р.):", chatId);
        } else if (start.equals("belay")) {
            double belay = Double.parseDouble(message);
            calculator.setBelay(belay);
            double totalSum = calculator.getTotalSum();
            double gas = calculator.getGas();
            calculator.setSalaryPerShift(totalSum, gas, belay);
            sendMessage("ИТОГО заработок за сутки: " + calculator.getSalaryPerShift(), chatId);
            calculator = new MoneyCalculator();
            line = "";
            start = "start";
            sendMessage("/save", chatId);
        }
    }

    private void resetOperation(long chatId) {
        line = "";
        start = "start";
        calculator = new MoneyCalculator();
        sendMessage("/balance Баланс (отдать в кассу)" +
                "\n\n/salary Заработная плата за смену" +
                "\n\n/dirty Всего за смену (зп, чай, премия)" +
                "\n___________________________________________" +
                "\n/exit - сброс опреции, все сначало", chatId);
    }

    private void addUser(Update update) {
        int userId = update.getMessage().getFrom().getId();
        UserDao userDao = new UserDaoImpl();
        List<User> listUsers = userDao.getAllUsers();
        boolean isExist = false;
        for (User user : listUsers) {
            if (userId == user.getUserId()) {
                isExist = true;


                sendMessage("Приветствую тебя юзер, у которого" +
                        "\n user_id: " + user.getUserId() +
                        "\nТы уже есть в системе!", update.getMessage().getChatId());


                break;
            }
        }
        if (!isExist) {
            User user = new User(
                    update.getMessage().getFrom().getId(),
                    update.getMessage().getFrom().getUserName(),
                    update.getMessage().getFrom().getFirstName(),
                    update.getMessage().getFrom().getLastName());
            userDao.insert(user);
        }
    }

}
