package demo.com.tcsa.model;

import java.util.List;

/**
  * @Date 2018/6/21 下午5:25
  */
public class ContestantTFModel {
    private int CID;
    private InvokeMethodModel invokeMethodModel;

    /**
     * 2018.06.25
     */
    private String testFragment;

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/8 下午10:41
      * @author sunweisong
      */
    public int getFragmentLength() {
        int fragLength = 0;
        List<StatementModel> instantiateStatementList = this.invokeMethodModel.getRelatedInstantiateStatementList();
        List<StatementModel> parameterizedStatementList = this.invokeMethodModel.getRelatedParameterizedStatementList();
        List<StatementModel> invokedStatementList = this.invokeMethodModel.getRelatedInvokedStatementList();
        if (instantiateStatementList != null) {
            for (StatementModel model : instantiateStatementList) {
                String statement = model.getContent();
                fragLength += statement.length();
            }
        }
        if (parameterizedStatementList != null) {
            for (StatementModel model : parameterizedStatementList) {
                String statement = model.getContent();
                fragLength += statement.length();
            }
        }
        if (invokedStatementList != null) {
            for (StatementModel model : invokedStatementList) {
                String statement = model.getContent();
                fragLength += statement.length();
            }
        }
        return fragLength;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/8 下午10:39
      * @author sunweisong
      */
    public int getStateNumber() {
        int stateNumber = 0;
        List<StatementModel> instantiateStatementList = this.invokeMethodModel.getRelatedInstantiateStatementList();
        List<StatementModel> parameterizedStatementList = this.invokeMethodModel.getRelatedParameterizedStatementList();
        List<StatementModel> invokedStatementList = this.invokeMethodModel.getRelatedInvokedStatementList();
        if (instantiateStatementList != null) {
            stateNumber += instantiateStatementList.size();
        }
        if (parameterizedStatementList != null) {
            stateNumber += parameterizedStatementList.size();
        }
        if (invokedStatementList != null) {
            stateNumber += invokedStatementList.size();
        }
        return stateNumber;
    }

    public String getTestFragment() {
        StringBuffer testFragmentBuffer = new StringBuffer();
        List<StatementModel> instantiateStatementList = this.invokeMethodModel.getRelatedInstantiateStatementList();
        List<StatementModel> parameterizedStatementList = this.invokeMethodModel.getRelatedParameterizedStatementList();
        List<StatementModel> invokedStatementList = this.invokeMethodModel.getRelatedInvokedStatementList();
        if (instantiateStatementList != null) {
            for (StatementModel model : instantiateStatementList) {
                String statement = model.getContent();
                testFragmentBuffer.append(statement + " ");
            }
        }
        if (parameterizedStatementList != null) {
            for (StatementModel model : parameterizedStatementList) {
                String statement = model.getContent();
                testFragmentBuffer.append(statement + " ");
            }
        }
        if (invokedStatementList != null) {
            for (StatementModel model : invokedStatementList) {
                String statement = model.getContent();
                testFragmentBuffer.append(statement + " ");
            }
        }
        if (testFragmentBuffer.length() > 0) {
            String temp = testFragmentBuffer.toString().trim();
            testFragmentBuffer = null;
            return temp;
        } else {
            return null;
        }
    }


    public ContestantTFModel(int CID, InvokeMethodModel invokeMethodModel) {
        this.CID = CID;
        this.invokeMethodModel = invokeMethodModel;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public InvokeMethodModel getInvokeMethodModel() {
        return invokeMethodModel;
    }

    public void setInvokeMethodModel(InvokeMethodModel invokeMethodModel) {
        this.invokeMethodModel = invokeMethodModel;
    }
}
