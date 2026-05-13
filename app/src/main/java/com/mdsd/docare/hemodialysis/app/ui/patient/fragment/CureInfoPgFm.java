package com.mdsd.docare.hemodialysis.app.ui.patient.fragment;

import android.R.bool;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.core.entity.AppContext;
import com.mdsd.docare.hemodialysis.app.core.entity.AppSingleton;
import com.mdsd.docare.hemodialysis.app.core.entity.Constants;
import com.mdsd.docare.hemodialysis.app.core.service.OnNetListener;
import com.mdsd.docare.hemodialysis.app.core.ui.fragment.BaseChildFragment;
import com.mdsd.docare.hemodialysis.app.core.ui.fragment.BaseFragment;
import com.mdsd.docare.hemodialysis.app.entity.server.MedCureInfo;
import com.mdsd.docare.hemodialysis.app.entity.server.MedDoctor;
import com.mdsd.docare.hemodialysis.app.entity.server.MedNurse;
import com.mdsd.docare.hemodialysis.app.service.MedPatientScheduleService;
import com.mdsd.docare.hemodialysis.app.util.ViewUtil;
import com.mdsd.library.ui.ClearEditText;
import com.mdsd.library.utils.StringUtils;
import com.mdsd.library.utils.ToastUtil;

/**
 * 治疗信息导管评估与治疗情况
 * <p>
 * <br>
 *
 * @author jianyu.l
 * @since 2014年8月19日
 */
public class CureInfoPgFm extends BaseChildFragment implements OnClickListener {
    static MedCureInfo currCureInfo = null;
    static View mView = null;
    EditText cure_et_remark;
    boolean firstLoad;
    //EditText cure_et_special_Adjust,cure_et_anticoagulant_Use,cure_et_special_Matter;
    //导管评估
    CheckBox cure_radiobtn_gdgl_s, cure_radiobtn_gdgl_f, cure_radiobtn_ht_s, cure_radiobtn_ht_f,
            cure_radiobtn_xlqk2_s, cure_radiobtn_xlqk2_f, cure_radiobtn_ccdjbhz_s, cure_radiobtn_ccdjbhz_f,
            cure_radiobtn_sx_s, cure_radiobtn_sx_f, cure_radiobtn_sy2_s, cure_radiobtn_sy2_f,
            cure_radiobtn_xs2_s, cure_radiobtn_xs2_f, cure_radiobtn_xlgr_s, cure_radiobtn_xlgr_f;
    //内瘘评估
    CheckBox cure_radiobtn_zc_s, cure_radiobtn_zc_f, cure_radiobtn_zc_n, cure_radiobtn_zy_s,
            cure_radiobtn_zy_f, cure_radiobtn_zy_n,//cure_radiobtn_xlqk_s,cure_radiobtn_xlqk_f,
            cure_radiobtn_sy_s, cure_radiobtn_sy_f, cure_radiobtn_xs_s, cure_radiobtn_xs_f,
            cure_radiobtn_hz_s, cure_radiobtn_hz_f;
    CheckBox cure_radiobtn_txqnx_0, cure_radiobtn_txqnx_1, cure_radiobtn_txqnx_2, cure_radiobtn_txqnx_3, cure_radiobtn_thwc;
    Spinner cure_spinner_zlhs, cure_spinner_zrys, cure_spinner_hdhs, cure_spinner_cchs;
    //Spinner cure_spinner_zhfs,cure_spinner_zhypf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.initView(inflater, container, R.layout.cure_info_pg_fm);
    }

    @Override
    public void setViewData() {
        super.setViewData();
        ViewUtil.buildSpiData(getActivity(), cure_spinner_cchs, MedNurse.class, AppSingleton.getInstance().medNurses);
        ViewUtil.buildSpiData(getActivity(), cure_spinner_zlhs, MedNurse.class, AppSingleton.getInstance().medNurses);
        //ViewUtil.buildSpiData(getActivity(), cure_spinner_hdhs, MedNurse.class, AppSingleton.getInstance().medNurses);
        ViewUtil.buildSpiData(getActivity(), cure_spinner_zrys, MedDoctor.class, AppSingleton.getInstance().medDoctors);
        //ViewUtil.buildSpiByMedConfig(getActivity(), cure_spinner_zhfs, Constants.Dic.DISPLACEMENT_MODE);
        //ViewUtil.buildSpiByMedConfig(getActivity(), cure_spinner_zhypf, Constants.Dic.DISPLACEMENT_RECIPE);
    }

    @Override
    public void setListeners() {
        super.setListeners();

        getCurrentView().findViewById(R.id.cure_btn_save).setOnClickListener(this);

        //导管评估
//        cure_radiobtn_gdgl_s.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_gdgl_f.isChecked()) {
//                        cure_radiobtn_gdgl_f.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_gdgl_f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_gdgl_s.isChecked()) {
//                        cure_radiobtn_gdgl_s.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_ht_s.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_ht_f.isChecked()) {
//                        cure_radiobtn_ht_f.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_ht_f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_ht_s.isChecked()) {
//                        cure_radiobtn_ht_s.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_xlqk2_s.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_xlqk2_f.isChecked()) {
//                        cure_radiobtn_xlqk2_f.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_xlqk2_f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_xlqk2_s.isChecked()) {
//                        cure_radiobtn_xlqk2_s.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_ccdjbhz_s.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_ccdjbhz_f.isChecked()) {
//                        cure_radiobtn_ccdjbhz_f.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_ccdjbhz_f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_ccdjbhz_s.isChecked()) {
//                        cure_radiobtn_ccdjbhz_s.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_sx_s.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_sx_f.isChecked()) {
//                        cure_radiobtn_sx_f.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_sx_f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_sx_s.isChecked()) {
//                        cure_radiobtn_sx_s.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_sy2_s.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_sy2_f.isChecked()) {
//                        cure_radiobtn_sy2_f.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_sy2_f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_sy2_s.isChecked()) {
//                        cure_radiobtn_sy2_s.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_xs2_s.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_xs2_f.isChecked()) {
//                        cure_radiobtn_xs2_f.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_xs2_f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_xs2_s.isChecked()) {
//                        cure_radiobtn_xs2_s.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_xlgr_s.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_xlgr_f.isChecked()) {
//                        cure_radiobtn_xlgr_f.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_xlgr_f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_xlgr_s.isChecked()) {
//                        cure_radiobtn_xlgr_s.setChecked(false);
//                    }
//                }
//            }
//        });

        //内瘘评估
//        cure_radiobtn_zc_s.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_zc_f.isChecked()) {
//                        cure_radiobtn_zc_f.setChecked(false);
//                    }
//                    if (cure_radiobtn_zc_n.isChecked()) {
//                        cure_radiobtn_zc_n.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_zc_f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_zc_s.isChecked()) {
//                        cure_radiobtn_zc_s.setChecked(false);
//                    }
//                    if (cure_radiobtn_zc_n.isChecked()) {
//                        cure_radiobtn_zc_n.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_zc_n.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_zc_s.isChecked()) {
//                        cure_radiobtn_zc_s.setChecked(false);
//                    }
//                    if (cure_radiobtn_zc_f.isChecked()) {
//                        cure_radiobtn_zc_f.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_zy_s.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_zy_f.isChecked()) {
//                        cure_radiobtn_zy_f.setChecked(false);
//                    }
//                    if (cure_radiobtn_zy_n.isChecked()) {
//                        cure_radiobtn_zy_n.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_zy_f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_zy_s.isChecked()) {
//                        cure_radiobtn_zy_s.setChecked(false);
//                    }
//                    if (cure_radiobtn_zy_n.isChecked()) {
//                        cure_radiobtn_zy_n.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_zy_n.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_zy_s.isChecked()) {
//                        cure_radiobtn_zy_s.setChecked(false);
//                    }
//                    if (cure_radiobtn_zy_f.isChecked()) {
//                        cure_radiobtn_zy_f.setChecked(false);
//                    }
//                }
//            }
//        });
		
		/*cure_radiobtn_xlqk_s.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1)
				{
					if(cure_radiobtn_xlqk_f.isChecked())
					{
						cure_radiobtn_xlqk_f.setChecked(false);
					}
				}
			}});*/
		
		/*cure_radiobtn_xlqk_f.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1)
				{
					if(cure_radiobtn_xlqk_s.isChecked())
					{
						cure_radiobtn_xlqk_s.setChecked(false);
					}
				}
			}});*/

//        cure_radiobtn_sy_s.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_sy_f.isChecked()) {
//                        cure_radiobtn_sy_f.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_sy_f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_sy_s.isChecked()) {
//                        cure_radiobtn_sy_s.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_xs_s.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_xs_f.isChecked()) {
//                        cure_radiobtn_xs_f.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_xs_f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_xs_s.isChecked()) {
//                        cure_radiobtn_xs_s.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_hz_s.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_hz_f.isChecked()) {
//                        cure_radiobtn_hz_f.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_hz_f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_hz_s.isChecked()) {
//                        cure_radiobtn_hz_s.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_txqnx_0.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_txqnx_1.isChecked()) {
//                        cure_radiobtn_txqnx_1.setChecked(false);
//                    }
//                    if (cure_radiobtn_txqnx_2.isChecked()) {
//                        cure_radiobtn_txqnx_2.setChecked(false);
//                    }
//                    if (cure_radiobtn_txqnx_3.isChecked()) {
//                        cure_radiobtn_txqnx_3.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_txqnx_1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_txqnx_0.isChecked()) {
//                        cure_radiobtn_txqnx_0.setChecked(false);
//                    }
//                    if (cure_radiobtn_txqnx_2.isChecked()) {
//                        cure_radiobtn_txqnx_2.setChecked(false);
//                    }
//                    if (cure_radiobtn_txqnx_3.isChecked()) {
//                        cure_radiobtn_txqnx_3.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_txqnx_2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_txqnx_0.isChecked()) {
//                        cure_radiobtn_txqnx_0.setChecked(false);
//                    }
//                    if (cure_radiobtn_txqnx_1.isChecked()) {
//                        cure_radiobtn_txqnx_1.setChecked(false);
//                    }
//                    if (cure_radiobtn_txqnx_3.isChecked()) {
//                        cure_radiobtn_txqnx_3.setChecked(false);
//                    }
//                }
//            }
//        });

//        cure_radiobtn_txqnx_3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    if (cure_radiobtn_txqnx_0.isChecked()) {
//                        cure_radiobtn_txqnx_0.setChecked(false);
//                    }
//                    if (cure_radiobtn_txqnx_1.isChecked()) {
//                        cure_radiobtn_txqnx_1.setChecked(false);
//                    }
//                    if (cure_radiobtn_txqnx_2.isChecked()) {
//                        cure_radiobtn_txqnx_2.setChecked(false);
//                    }
//                }
//            }
//        });

        ClearEditText cure_ed_txhtz = (ClearEditText) getCurrentView().findViewById(R.id.cure_ed_txhtz);
        cure_ed_txhtz.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
//				if(!firstLoad)
//				{
//					java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.0");
//					ClearEditText cure_ed_txqtz=(ClearEditText)getCurrentView().findViewById(R.id.cure_ed_txqtz);
//					Double beforeValue = !StringUtils.isEmpty(cure_ed_txqtz.getText().toString()) ? Double.valueOf(cure_ed_txqtz.getText().toString()) : 0;
//					Double afterValue = !StringUtils.isEmpty(arg0.toString()) ? Double.valueOf(arg0.toString()) : 0;
//					if(afterValue!=0)
//					{
//						ClearEditText cure_ed_sjts = (ClearEditText) getCurrentView().findViewById(R.id.cure_ed_sjts);
//						Double sjts = !StringUtils.isEmpty(cure_ed_sjts.getText().toString()) ? Double.valueOf(cure_ed_sjts.getText().toString()) : 0;
//						if (sjts != 0) {
//							cure_ed_sjts.setText(String.valueOf(myformat.format(beforeValue - afterValue)));
//						}
//					}
//				}
            }
        });

//		cure_radiobtn_thwc.setOnCheckedChangeListener(new OnCheckedChangeListener(){
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				if(isChecked)
//				{
//					ClearEditText cure_ed_txhwc=(ClearEditText)getCurrentView().findViewById(R.id.cure_ed_txhwc);
//					cure_ed_txhwc.setVisibility(View.VISIBLE);
//					cure_ed_txhwc.setText("卧床");
//
//					ClearEditText cure_ed_txhtz=(ClearEditText)getCurrentView().findViewById(R.id.cure_ed_txhtz);
//					cure_ed_txhtz.setVisibility(View.GONE);
//				}
//				else
//				{
//					ClearEditText cure_ed_txhwc=(ClearEditText)getCurrentView().findViewById(R.id.cure_ed_txhwc);
//					cure_ed_txhwc.setVisibility(View.GONE);
//
//					ClearEditText cure_ed_txhtz=(ClearEditText)getCurrentView().findViewById(R.id.cure_ed_txhtz);
//					cure_ed_txhtz.setVisibility(View.VISIBLE);
//				}
//			}
//		});
    }

    @Override
    public void findViews() {
        super.findViews();
        mView = getCurrentView().findViewById(R.id.rootView);
        //cure_et_remark = (EditText) getCurrentView().findViewById(R.id.cure_et_remark);
        //cure_radiobtn_gdgl_s=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_gdgl_s);
        //cure_radiobtn_gdgl_f=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_gdgl_f);
        //cure_radiobtn_ht_s=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_ht_s);
        //cure_radiobtn_ht_f=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_ht_f);
        //cure_radiobtn_xlqk2_s=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_xlqk2_s);
        //cure_radiobtn_xlqk2_f=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_xlqk2_f);
        //cure_radiobtn_ccdjbhz_s=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_ccdjbhz_s);
        //cure_radiobtn_ccdjbhz_f=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_ccdjbhz_f);
        //cure_radiobtn_sx_s=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_sx_s);
        //cure_radiobtn_sx_f=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_sx_f);
        //cure_radiobtn_sy2_s=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_sy2_s);
        //cure_radiobtn_sy2_f=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_sy2_f);
        //cure_radiobtn_xs2_s=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_xs2_s);
        //cure_radiobtn_xs2_f=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_xs2_f);
        //cure_radiobtn_xlgr_s=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_xlgr_s);
        //cure_radiobtn_xlgr_f=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_xlgr_f);

        //cure_radiobtn_zc_s=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_zc_s);
        //cure_radiobtn_zc_f=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_zc_f);
        //cure_radiobtn_zc_n=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_zc_n);
        //cure_radiobtn_zy_s=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_zy_s);
        //cure_radiobtn_zy_f=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_zy_f);
        //cure_radiobtn_zy_n=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_zy_n);
        //cure_radiobtn_xlqk_s=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_xlqk_s);
        //cure_radiobtn_xlqk_f=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_xlqk_f);
        //cure_radiobtn_sy_s=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_sy_s);
        //cure_radiobtn_sy_f=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_sy_f);
        //cure_radiobtn_xs_s=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_xs_s);
        //cure_radiobtn_xs_f=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_xs_f);
        //cure_radiobtn_hz_s=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_hz_s);
        //cure_radiobtn_hz_f=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_hz_f);

        //cure_radiobtn_txqnx_0=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_txqnx_0);
        //cure_radiobtn_txqnx_1=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_txqnx_1);
        //cure_radiobtn_txqnx_2=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_txqnx_2);
        //cure_radiobtn_txqnx_3=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_txqnx_3);
        //cure_radiobtn_thwc=(CheckBox) getCurrentView().findViewById(R.id.cure_radiobtn_thwc);

        cure_spinner_cchs = (Spinner) getCurrentView().findViewById(R.id.cure_spinner_cchs);
        cure_spinner_zlhs = (Spinner) getCurrentView().findViewById(R.id.cure_spinner_zlhs);
        //cure_spinner_hdhs = (Spinner)getCurrentView().findViewById(R.id.cure_spinner_hdhs);
        cure_spinner_zrys = (Spinner) getCurrentView().findViewById(R.id.cure_spinner_zrys);
        //cure_spinner_zhfs = (Spinner)getCurrentView().findViewById(R.id.cure_spinner_zhfs);
        //cure_spinner_zhypf = (Spinner)getCurrentView().findViewById(R.id.cure_spinner_zhypf);
        //cure_et_special_Adjust = (EditText) getCurrentView().findViewById(R.id.cure_et_special_Adjust);
        //cure_et_anticoagulant_Use = (EditText) getCurrentView().findViewById(R.id.cure_et_anticoagulant_Use);
        //cure_et_special_Matter = (EditText) getCurrentView().findViewById(R.id.cure_et_special_Matter);
    }

    //评估类型
    private void SetPGNX(String pgType) {
        //内篓评估
        if (pgType.equals("内瘘")) {
            //getCurrentView().findViewById(R.id.cure_formItem_basket_zc).setVisibility(View.VISIBLE);
            //getCurrentView().findViewById(R.id.cure_formItem_basket_zy).setVisibility(View.VISIBLE);
            //getCurrentView().findViewById(R.id.cure_formItem_basket_xlqk).setVisibility(View.VISIBLE);
            //getCurrentView().findViewById(R.id.cure_formItem_basket_sy).setVisibility(View.VISIBLE);
            //getCurrentView().findViewById(R.id.cure_formItem_basket_xs).setVisibility(View.VISIBLE);
            //getCurrentView().findViewById(R.id.cure_formItem_basket_hz).setVisibility(View.VISIBLE);

            //getCurrentView().findViewById(R.id.cure_formItem_vascular_gdgl).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_ht).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_xlqk).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_ccdjbhz).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_sx).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_sy).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_xs).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_xlgr).setVisibility(View.GONE);
        } else if (pgType.equals("导管")) {
            //getCurrentView().findViewById(R.id.cure_formItem_basket_zc).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_basket_zy).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_basket_xlqk).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_basket_sy).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_basket_xs).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_basket_hz).setVisibility(View.GONE);

            //getCurrentView().findViewById(R.id.cure_formItem_vascular_gdgl).setVisibility(View.VISIBLE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_ht).setVisibility(View.VISIBLE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_xlqk).setVisibility(View.VISIBLE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_ccdjbhz).setVisibility(View.VISIBLE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_sx).setVisibility(View.VISIBLE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_sy).setVisibility(View.VISIBLE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_xs).setVisibility(View.VISIBLE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_xlgr).setVisibility(View.VISIBLE);
        } else {
            //getCurrentView().findViewById(R.id.cure_formItem_basket_zc).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_basket_zy).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_basket_xlqk).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_basket_sy).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_basket_xs).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_basket_hz).setVisibility(View.GONE);

            //getCurrentView().findViewById(R.id.cure_formItem_vascular_gdgl).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_ht).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_xlqk).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_ccdjbhz).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_sx).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_sy).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_xs).setVisibility(View.GONE);
            //getCurrentView().findViewById(R.id.cure_formItem_vascular_xlgr).setVisibility(View.GONE);
        }
    }

    @Override
    public void setCommonObjectValue(Object t) {
        // 根据透析处方中血管通路类型显示评估项目
        firstLoad = true;
        MedCureInfo cureInfo = currCureInfo = (MedCureInfo) t;
        ViewUtil.setValueToView(getCurrentView().findViewById(R.id.rootView), cureInfo);

//		if(!StringUtils.isEmpty(cureInfo.getDoctor_Advice()))
//		{
//			cure_et_remark.setText(cureInfo.getDoctor_Advice());
//		}

        if (cure_spinner_zlhs.getSelectedItem() == null || ((MedNurse) cure_spinner_zlhs.getSelectedItem()).getEMP_NO() == "") {
            for (int i = 0; i < cure_spinner_zlhs.getCount(); i++) {
                if (cure_spinner_zlhs.getItemAtPosition(i) instanceof MedNurse && ((MedNurse) cure_spinner_zlhs.getItemAtPosition(i)).getEMP_NO().equals(AppContext.getInstance().currentUser.getEMP_NO())) {
                    cure_spinner_zlhs.setSelection(i);
                }
            }
        }

//		if(cureInfo.getUfr()==0)
//		{
//			((ClearEditText) getCurrentView().findViewById(R.id.cure_ed_yjts)).setText("0");
//		}

//		if(cureInfo.getVascular_Access_Type().toUpperCase().equals("TRUE"))
//		{
//			ClearEditText cure_ed_txqwc=(ClearEditText)getCurrentView().findViewById(R.id.cure_ed_txqwc);
//			cure_ed_txqwc.setVisibility(View.VISIBLE);
//			cure_ed_txqwc.setText("卧床");
//
//			ClearEditText cure_ed_txqtz=(ClearEditText)getCurrentView().findViewById(R.id.cure_ed_txqtz);
//			cure_ed_txqtz.setVisibility(View.GONE);
//		}
//		else
//		{
//			ClearEditText cure_ed_txqwc=(ClearEditText)getCurrentView().findViewById(R.id.cure_ed_txqwc);
//			cure_ed_txqwc.setVisibility(View.GONE);
//
//			ClearEditText cure_ed_txqtz=(ClearEditText)getCurrentView().findViewById(R.id.cure_ed_txqtz);
//			cure_ed_txqtz.setVisibility(View.VISIBLE);
//		}

//		if(cureInfo.getIn_Bed().equals("1"))
//		{
//			ClearEditText cure_ed_txhwc=(ClearEditText)getCurrentView().findViewById(R.id.cure_ed_txhwc);
//			cure_ed_txhwc.setVisibility(View.VISIBLE);
//			cure_ed_txhwc.setText("卧床");
//
//			ClearEditText cure_ed_txhtz=(ClearEditText)getCurrentView().findViewById(R.id.cure_ed_txhtz);
//			cure_ed_txhtz.setVisibility(View.GONE);
//		}
//		else
//		{
//			ClearEditText cure_ed_txhwc=(ClearEditText)getCurrentView().findViewById(R.id.cure_ed_txhwc);
//			cure_ed_txhwc.setVisibility(View.GONE);
//
//			ClearEditText cure_ed_txhtz=(ClearEditText)getCurrentView().findViewById(R.id.cure_ed_txhtz);
//			cure_ed_txhtz.setVisibility(View.VISIBLE);
//		}

        firstLoad = false;
		
		/*if(!StringUtils.isEmpty(cureInfo.getDisplacement_Special_Adjust()))
		{
			cure_et_special_Adjust.setText(cureInfo.getDisplacement_Special_Adjust());
		}
		
		if(!StringUtils.isEmpty(cureInfo.getAnticoagulant_Use()))
		{
			cure_et_anticoagulant_Use.setText(cureInfo.getAnticoagulant_Use());
		}
		
		if(!StringUtils.isEmpty(cureInfo.getSpecial_Matter()))
		{
			cure_et_special_Matter.setText(cureInfo.getSpecial_Matter());
		}*/

        ClearEditText cure_ed_sctxhtz = (ClearEditText) getCurrentView().findViewById(R.id.cure_ed_sctxhtz);
        Double lastValue = !StringUtils.isEmpty(cure_ed_sctxhtz.getText().toString()) ? Double.valueOf(cure_ed_sctxhtz.getText().toString()) : 0;
        if (lastValue == 0) {
            MedPatientScheduleService.getLastTimeDryWeightById(currCureInfo.getHemodialysis_Id(), currCureInfo.getCure_Create_Date(),
                    new OnNetListener<String>() {

                        @Override
                        public void onResponse(String object) {
                            if (object != null && !object.equals("0.0")) {
                                ((ClearEditText) getCurrentView().findViewById(R.id.cure_ed_sctxhtz)).setText(object);
                            }
                        }

                        @Override
                        public void onError(String errorMessage) {
                            ToastUtil.show(getContext(), errorMessage);
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        //检查透后体重是否大于透前体重，若大于，给予提示，不予保存
        EditText cure_ed_txqtz = (EditText) getCurrentView().findViewById(R.id.cure_ed_txqtz);
        EditText cure_ed_txhtz = (EditText) getCurrentView().findViewById(R.id.cure_ed_txhtz);
        String before = StringUtils.isEmpty(cure_ed_txqtz.getText().toString()) ? "0" : cure_ed_txqtz.getText().toString();
        String after = StringUtils.isEmpty(cure_ed_txhtz.getText().toString()) ? "0" : cure_ed_txhtz.getText().toString();
        Double beforeWeight = Double.parseDouble(before);
        Double afterWeight = Double.parseDouble(after);
        if (afterWeight != 0 && beforeWeight != 0) {
            if (afterWeight > beforeWeight) {
                ToastUtil.show(getContext(), "透后体重不可能大于透前体重，请核对");
                return;
            }
        }

        //调用父fragment让其顺序调用每个子fragment的changeObjectByView
        ((BaseFragment) getParentFragment()).onChildFragmentCall();
    }

    @Override
    public void changeObjectByView(Object t) {
        MedCureInfo cureInfo = (MedCureInfo) t;
        //cureInfo.setDoctor_Advice(cure_et_remark.getText().toString());
        //cureInfo.setDisplacement_Special_Adjust(cure_et_special_Adjust.getText().toString());
        //cureInfo.setAnticoagulant_Use(cure_et_anticoagulant_Use.getText().toString());
        //cureInfo.setSpecial_Matter(cure_et_special_Matter.getText().toString());
        cureInfo.setVascular_Access_Firm("");
        cureInfo.setVascular_Access_Glide("");
        cureInfo.setVascular_Access_Swelling("");
        cureInfo.setVascular_Access_Errhyisis("");
        cureInfo.setVascular_Access_Thrombus("");
        cureInfo.setVascular_Access_Blood("");
        cureInfo.setVascular_Access_Blood_Infect("");
        cureInfo.setIn_Basket_Plaster_Allergy("");

        cureInfo.setIn_Basket_Tremor("");
        cureInfo.setIn_Basket_Noise("");
        cureInfo.setIn_Basket_Clean("");
        cureInfo.setIn_Basket_Ecchymosis("");
        cureInfo.setIn_Basket_Vascular_Elasticity("");
        cureInfo.setIn_Basket_Red_Hot("");
        //cureInfo.setIn_Bed(cure_radiobtn_thwc.isChecked() ? "1" : "0");
        ViewUtil.getValueFromView(getCurrentView().findViewById(R.id.rootView), cureInfo);
    }

    /**
     * x血管通路选择改变
     */
    public void onSpinnerXgtlChnage(String selectString) {
        if (currCureInfo == null)
            return;

        if (selectString.contains("内瘘")) {
            SetPGNX("内瘘");
            //currCureInfo.setVascular_Access_Type("内瘘评估");
            //ViewUtil.setValueToView(mView, currCureInfo);
        } else if (selectString.contains("导管") || selectString.contains("置管")) {
            SetPGNX("导管");
            //currCureInfo.setVascular_Access_Type("导管评估");
            //ViewUtil.setValueToView(mView, currCureInfo);
        } else {
            SetPGNX("其它");
        }
    }
}
