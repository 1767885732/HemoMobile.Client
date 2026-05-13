package com.mdsd.docare.hemodialysis.app.ui.patient;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.core.entity.Constants;
import com.mdsd.docare.hemodialysis.app.core.service.OnNetListener;
import com.mdsd.docare.hemodialysis.app.core.ui.activity.BaseFragmentActivity;
import com.mdsd.docare.hemodialysis.app.core.ui.fragment.BaseFragment;
import com.mdsd.docare.hemodialysis.app.entity.server.MedCureInfo;
import com.mdsd.docare.hemodialysis.app.entity.server.MedPatientSchedule;
import com.mdsd.docare.hemodialysis.app.entity.server.MedRecipeInfo;
import com.mdsd.docare.hemodialysis.app.service.MedPatientScheduleService;
import com.mdsd.docare.hemodialysis.app.util.DataUtil;
import com.mdsd.library.utils.ToastUtil;
import com.mdsd.library.utils.log.Log;

/**
 * 病人的功能主页
 * <p>
 * <br>
 *
 * @author jianyu.l
 * @since 2014年8月18日
 */
public class PatientTabActivity extends BaseFragmentActivity {

    // view
    TabHost tabHost;
    ProgressDialog progressDialog;

    // variable
    public static MedPatientSchedule currMedPatientSchedule;
    private boolean isFirstLoad = true;// 是否第一次加载
    private String currentTabId = "治疗信息";

    /**
     * 当前患者的治疗单信息
     */
    public static MedCureInfo currMedCureInfo;

    /**
     * 当前患者的处方单信息
     */
    public static MedRecipeInfo currMedRecipeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            currMedPatientSchedule = (MedPatientSchedule) savedInstanceState.getSerializable(Constants.ExtraKey.MED_PATIENT_SCHEDULE);

        super.onCreate(savedInstanceState, R.layout.patient_tab_activity, true);
    }

    @Override
    public void beforeInitView() {
        super.beforeInitView();

        // 加载治疗信息数据
        currMedPatientSchedule = (MedPatientSchedule) getIntent().getSerializableExtra(Constants.ExtraKey.MED_PATIENT_SCHEDULE);

        // 治疗状态

        if (currMedPatientSchedule != null) {
            getPatientScheduleByDateAndHemoId(currMedPatientSchedule.getDIALYSIS_DATE(), currMedPatientSchedule.getHEMODIALYSIS_ID());
            int state = DataUtil.getCureState(currMedPatientSchedule.getSTART_TIME(), currMedPatientSchedule.getEND_TIME());
            // getMainCureByRecipeId(currMedPatientSchedule.getRECIPE_ID());
            // getRecipeByRecipeId(currMedPatientSchedule.getRECIPE_ID());
            String tag = "";
            String result = currMedPatientSchedule.getINFECTIOUS_CHECK_RESULT();
            if (result.equals("乙肝")) {
                tag = "<HBV>";
            } else if (result.equals("丙肝")) {
                tag = "<HCV>";
            } else if (result.equals("梅毒")) {
                tag = "<RPR>";
            } else if (result.equals("艾滋病")) {
                tag = "<HIV>";
            }
            getActionBar().setTitle(String.format(
                    "%s %s%s %s %s %s %s号床 %s",
                    currMedPatientSchedule.getSTART_TIME(),
                    currMedPatientSchedule.getPATIENTNAME(),
                    tag,
                    currMedPatientSchedule.getSEX(),
                    state == 0 ? "治疗中" : DataUtil.getCureStatus(String.valueOf(state)),
                    currMedPatientSchedule.getAREANAME(),
                    currMedPatientSchedule.getBEDNAME(),
                    currMedPatientSchedule.getHEMODIALYSIS_ID()));
        } else {
            ToastUtil.show(this, "患者数据有误");
            finish();
        }

    }

    @Override
    public void findViews() {
        super.findViews();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constants.ExtraKey.MED_PATIENT_SCHEDULE, currMedPatientSchedule);
    }

    // -----------------
    // private method
    // -----------------

    /**
     * 子页面加载
     */
    void initChildView() {
        if (isFirstLoad) {
            tabHost = (TabHost) findViewById(android.R.id.tabhost);
            tabHost.setup();

            tabHost.addTab(getTabSpec(
                    getResources().getString(R.string.tab_cure_info),
                    R.drawable.ic_cure_info, R.id.tabCureInfo));
            tabHost.addTab(getTabSpec(
                    getResources().getString(R.string.tab_temp_orders),
                    R.drawable.ic_temp_orders, R.id.tabTempOrders));
            tabHost.addTab(getTabSpec(
                    getResources().getString(R.string.tab_dialysis_param),
                    R.drawable.ic_dialysis_param, R.id.tabDialysisParam));
            tabHost.addTab(getTabSpec(
                    getResources().getString(R.string.tab_dialysis_summary),
                    R.drawable.ic_dialysis_summary, R.id.tabDialysisSummary));
            isFirstLoad = false;
        }

        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.d("tabhost changed is calling");
                //BaseFragment currFragment = null;
                currentTabId = tabId;
                onSelected();
				
				/*if(tabId.equals(getResources().getString(R.string.tab_cure_info)))
				{
					currFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.tabCureInfo);
					
				}
				
				if(tabId.equals(getResources().getString(R.string.tab_temp_orders)))
				{
					currFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.tabTempOrders);
				}
				
				if(tabId.equals(getResources().getString(R.string.tab_dialysis_param)))
				{
					currFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.tabDialysisParam);
				}
				
				if(tabId.equals(getResources().getString(R.string.tab_dialysis_summary)))
				{
					currFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.tabDialysisSummary);
				}
				
				if(currFragment != null)
					currFragment.onSelected();*/

            }
        });

        ((BaseFragment) getSupportFragmentManager().findFragmentById(R.id.tabCureInfo)).onSelected();
    }

    @SuppressLint("InflateParams")
    TabSpec getTabSpec(String tag, int icon, int contentId) {
        View v = getLayoutInflater().inflate(R.layout.tab_item, null);
        TextView tv = (TextView) v.findViewById(R.id.tabItem);
        tv.setText(tag);
        tv.setCompoundDrawablesWithIntrinsicBounds(0, icon, 0, 0);
        return tabHost.newTabSpec(tag).setIndicator(tv).setContent(contentId);
    }

    /**
     * 根据透析日期和透析编号获取排班信息
     */
    void getPatientScheduleByDateAndHemoId(String date, String hemoId) {
        if (progressDialog == null)
            progressDialog = ProgressDialog.show(this, "", getString(R.string.dialog_data_commit), true);
        else
            progressDialog.show();


        MedPatientScheduleService.getPatientScheduleByDateAndHemoId(date, hemoId, new OnNetListener<MedPatientSchedule>() {
            @Override
            public void onResponse(MedPatientSchedule patientSchedule) {
                progressDialog.cancel();
                currMedPatientSchedule = patientSchedule;
                getMainCureByRecipeId(currMedPatientSchedule.getRECIPE_ID());
                getRecipeByRecipeId(currMedPatientSchedule.getRECIPE_ID());
            }

            @Override
            public void onError(String errorMessage) {
                progressDialog.cancel();
                ToastUtil.show(PatientTabActivity.this, errorMessage);
                finish();
            }
        });
    }

    /**
     * 获取治疗信息根据RecipeId
     */
    void getMainCureByRecipeId(String recipeId) {
        if (progressDialog == null)
            progressDialog = ProgressDialog.show(this, "", getString(R.string.dialog_data_commit), true);
        else
            progressDialog.show();


        MedPatientScheduleService.getMainCureByRecipeId(recipeId, new OnNetListener<MedCureInfo>() {
            @Override
            public void onResponse(MedCureInfo cureInfo) {
                progressDialog.cancel();
                currMedCureInfo = cureInfo;

                initChildView();
            }

            @Override
            public void onError(String errorMessage) {
                progressDialog.cancel();
                ToastUtil.show(PatientTabActivity.this, errorMessage);
                finish();
            }
        });
    }

    /**
     * 获取处方信息根据RecipeId
     */
    void getRecipeByRecipeId(String recipeId) {
        if (progressDialog == null)
            progressDialog = ProgressDialog.show(this, "", getString(R.string.dialog_data_commit), true);
        else
            progressDialog.show();


        MedPatientScheduleService.getRecipeByRecipeId(recipeId, new OnNetListener<MedRecipeInfo>() {
            @Override
            public void onResponse(MedRecipeInfo recipeInfo) {
                progressDialog.cancel();
                currMedRecipeInfo = recipeInfo;
            }

            @Override
            public void onError(String errorMessage) {
                progressDialog.cancel();
                ToastUtil.show(PatientTabActivity.this, errorMessage);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.patient_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuRefresh) {
            getMainCureByRecipeId(currMedPatientSchedule.getRECIPE_ID());
            getRecipeByRecipeId(currMedPatientSchedule.getRECIPE_ID());
            onSelected();
        }

        return super.onOptionsItemSelected(item);
    }

    private void onSelected() {
        BaseFragment currFragment = null;

        if (currentTabId.equals(getResources().getString(R.string.tab_cure_info))) {
            currFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.tabCureInfo);
        }

        if (currentTabId.equals(getResources().getString(R.string.tab_temp_orders))) {
            currFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.tabTempOrders);
        }

        if (currentTabId.equals(getResources().getString(R.string.tab_dialysis_param))) {
            currFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.tabDialysisParam);
        }

        if (currentTabId.equals(getResources().getString(R.string.tab_dialysis_summary))) {
            currFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.tabDialysisSummary);
        }

        if (currFragment != null)
            currFragment.onSelected();
    }
}
