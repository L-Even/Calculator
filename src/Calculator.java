import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Calculator {

    private BigDecimal preTotal;
    private BigDecimal newNumber;
    private String operate;
    private List<BigDecimal> lastTotalList = new ArrayList<>();
    private List<String> lastOperateList = new ArrayList<>();
    private List<BigDecimal> lastNumberList = new ArrayList<>();
    private int lastOperateIndex = -1;
    private int redoMax = -1;
    private int scale = 2;

    public BigDecimal getPreTotal() {
        return preTotal;
    }

    public void setPreTotal(BigDecimal preTotal) {
        this.preTotal = preTotal;
    }

    public BigDecimal getNewNumber() {
        return newNumber;
    }

    public void setNewNumber(BigDecimal newNumber) {
        if (null == preTotal) {
            preTotal = newNumber;
        } else {
            this.newNumber = newNumber;
        }
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public List<BigDecimal> getLastTotalList() {
        return lastTotalList;
    }

    public void setLastTotalList(List<BigDecimal> lastTotalList) {
        this.lastTotalList = lastTotalList;
    }

    public List<String> getLastOperateList() {
        return lastOperateList;
    }

    public void setLastOperateList(List<String> lastOperateList) {
        this.lastOperateList = lastOperateList;
    }

    public List<BigDecimal> getLastNumberList() {
        return lastNumberList;
    }

    public void setLastNumberList(List<BigDecimal> lastNumberList) {
        this.lastNumberList = lastNumberList;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void count() {
        preTotal = null == preTotal ? BigDecimal.ZERO : preTotal;
        if (null == operate) {
            System.out.println("请输入操作");
            return;
        }
        if (null != newNumber) {
            BigDecimal result = countTwoNumber(preTotal, operate, newNumber);
            if (lastOperateIndex == -1) {
                lastTotalList.add(preTotal);
                lastOperateList.add(operate);
                lastNumberList.add(newNumber);
            } else {
                lastOperateIndex++;
                redoMax = lastOperateIndex;
                lastTotalList.set(lastOperateIndex, result);
                lastOperateList.set(lastOperateIndex - 1, operate);
                lastNumberList.set(lastOperateIndex - 1, newNumber);
            }
            preTotal = result;
            operate = null;
            newNumber = null;
        }
    }

    public String display() {
        StringBuilder outputStr = new StringBuilder();
        if (null != preTotal) {
            outputStr.append(preTotal.setScale(scale, RoundingMode.HALF_UP));
        }
        if (null != operate) {
            outputStr.append(operate);
        }
        if (null != newNumber) {
            outputStr.append(newNumber);
        }
        return outputStr.toString();
    }

    private BigDecimal countTwoNumber(BigDecimal preNumber, String operate, BigDecimal newNumber) {
        BigDecimal result = BigDecimal.ZERO;
        operate = null == operate ? "+" : operate;
        switch (operate) {
            case "+":
                result = preNumber.add(newNumber);
                break;
            case "-":
                result = preNumber.subtract(newNumber).setScale(scale, RoundingMode.HALF_UP);
                break;
            case "*":
                result = preNumber.multiply(newNumber).setScale(scale, RoundingMode.HALF_UP);
                break;
            case "/":
                result = preNumber.divide(newNumber, RoundingMode.HALF_UP);
                break;
        }
        return result;
    }

    public void undo() {
        if (null != preTotal && lastOperateIndex == -1) {
            lastTotalList.add(preTotal);
            operate = null;
            newNumber = null;
        }

        if (lastTotalList.size() == 0) {
            System.out.println("无操作");
        } else if (lastTotalList.size() == 1) {
            System.out.println("undo后值：0，undo前值" + preTotal);
            preTotal = BigDecimal.ZERO;
            return;
        } else {
            if (lastOperateIndex == -1) {
                lastOperateIndex = lastOperateList.size() - 1;
            } else {
                if (lastOperateIndex - 1 < 0) {
                    System.out.println("无法undo");
                    return;
                }
                lastOperateIndex--;
            }
        }
        System.out.println("undo前的值：" + preTotal + "，undo后的值：" + lastTotalList.get(lastOperateIndex)
                + "，undo的操作：" + lastOperateList.get(lastOperateIndex) + "，undo的操作值：" + lastNumberList.get(lastOperateIndex));
        preTotal = lastTotalList.get(lastOperateIndex);
        operate = null;
        newNumber = null;
    }

    public void redo() {
        if (lastOperateIndex > -1) {
            if (lastOperateIndex + 1 >= lastTotalList.size() || lastOperateIndex == redoMax) {
                System.out.println("无法redo");
                return;
            }

            lastOperateIndex++;
            System.out.println("redo前的值：" + preTotal + "，redo后的值：" + lastTotalList.get(lastOperateIndex)
                    + "，redo的操作：" + lastOperateList.get(lastOperateIndex - 1) + "，redo的操作数：" + lastNumberList.get(lastOperateIndex - 1));
            preTotal = lastTotalList.get(lastOperateIndex);
            operate = null;
            newNumber = null;
        }
    }
}
