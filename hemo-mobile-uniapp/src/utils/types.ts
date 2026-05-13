export interface MedUser {
  USER_ID: string
  LOGIN_NAME: string
  LOGIN_PWD: string
  USER_NAME: string
  IS_VALID: string
  CREATED_DATE: string
  DEPT_ID: string
  MEMO: string
  EMP_NO: string
}

export interface MedPatientSchedule {
  PATIENT_SCHEDULE_ID: string
  PATIENT_ID: string
  PAT_PIC: string
  PATIENTNAME: string
  SEX: string
  MONITOR_LABEL: string
  DIALYSIS_DATE: string
  BANCI_ID: string
  DIALYSIS_ROOM_ID: string
  BED_NUMBER: string
  BEDNAME: string
  START_TIME: string
  END_TIME: string
  STATUS: string
  HEMODIALYSIS_ID: string
  REMARK: string
  RECIPE_ID: string
  PURIFIER_MODEL_ID: string
  USER_ID: string
  FOCUS_LEVEL: string
  MACHINE_NAME: string
  MODELNAME: string
  AREANAME: string
  INFECTIOUS_CHECK_RESULT: string
  IS_CRRT: string
  PURIFICATION_MODE: string
  MODEL_NAME: string
  FREQUENCY_HOURS: string
  CHECK_NUM: string
  CHECK_DATE: string
}

export interface MedCureInfo {
  cure_Id: string
  recipe_Id: string
  hemodialysis_Id: string
  recipe_Type: string
  calcium_Ion: number | null
  cure_Status: string
  doctor_Id: string
  recipe_Date: string
  bloow_Flow: number | null
  dialysate_Flow: number | null
  dialysate_Temperature: number | null
  ufr: number | null
  sodion: number | null
  potassium_Ion: number | null
  perform_Schedule: string
  nurse_Id: string
  purification_Mode: string
  clean_Up_Times: number
  frequency_Hours: number | null
  begin_Time: string
  end_Time: string
  last_Time_Dry_Weight: number | null
  dry_Weight: number | null
  before_Dry_Weight: number | null
  after_Dry_Weight: number | null
  before_Systolic_Pressure: number | null
  before_Diastolic_Pressure: number | null
  after_Systolic_Pressure: number | null
  after_Diastolic_Pressure: number | null
  dry_Water_Value: number | null
  before_Temperature: number | null
  after_Temperature: number | null
  before_Heart_Rate: number | null
  after_Heart_Rate: number | null
  primary_Nurse: string
  primary_Doctor: string
  puncture_Nurse: string
  machine_Id: string
  vascular_Access_Id: string
  heparin_Species: string
  first_Heparin: number | null
  dosis_Sustentativa: number | null
  machine_Type: string
  purifier_Name: string
  purifier_M2: number | null
  use_Type: string
  reuse_Times: number | null
  a_Liquid: string
  b_Liquid: string
  bircarbonate: number | null
  amylaceum: number | null
  summary: string
  cure_Create_Date: string
  vascular_Access_Firm: string
  vascular_Access_Glide: string
  vascular_Access_Swelling: string
  vascular_Access_Errhyisis: string
  vascular_Access_Thrombus: string
  vascular_Access_Blood: string
  vascular_Access_Blood_Infect: string
  filtration_Displacement_Liquid: number | null
  filtration_Percolate: number | null
  displacement_Liquid: number | null
  percolate: number | null
  doctor_Advice: string
  summary2: string
  summary3: string
  check_Nurse: string
  first_Drug_Unit: string
  second_Drug_Unit: string
  vein: string
  dry_Weight_Tag: string
  before_Dry_Weight_Tag: string
  after_Dry_Weight_Tag: string
  reuse_Times_Tag: string
  machine_Id_Tag: string
  blood_Up: string
  blood_Type: string
  blood_Transfusion: string
  coagulation_In_Dialyser: string
  in_Basket_Clean: string
  in_Basket_Red_Hot: string
  in_Basket_Ecchymosis: string
  in_Basket_Tremor: string
  in_Basket_Noise: string
  in_Basket_Vascular_Elasticity: string
  in_Basket_Vascular_Other: string
  in_Basket_Wound_Allergy: string
  in_Basket_Plaster_Allergy: string
  vascular_Access_Type: string
  subjective_Comfort: string
  before_BP: number | null
  after_BP: number | null
  frequency_Minute: number | null
  displacement_Mode: string
  displacement_Recipe: string
  displacement_Special_Adjust: string
  anticoagulant_Use: string
  special_Matter: string
  ufr2: number | null
  displacement_Flow: number | null
  uf: number | null
  sum_Uf: number | null
  focus_Level: string
  senses: string
  allergic: string
  bt: string
  bp: string
  br: string
  afterbt: string
  afterbp: string
  afterbr: string
  in_Bed: string
  actual_Cleanup_Hour: number | null
  actual_Cleanup_Minute: number | null
}

export interface MedHemoParameter {
  hemodialysis_Parameters_Id: string
  cure_Id: string
  recipe_Id: string
  create_Date: string
  venous_Pressure: number
  transmembrane_Pressure: number
  temperature: number
  systolic_Pressure: number
  diastolic_Pressure: number
  cardiotach: number
  breath: number
  kt_v: string
  cure_Mode: string
  clinical_Manifestation: string
  blood_Flow: number
  sodium_Ion: number
  dialysate_Rate: number
  urf: number
  online_Clearance_Rate: number
  conductivity: number
  nurse_Id: string
  displacement: number
  vascular_Access_Errhyisis: string
  vascular_Access_Glide: string
  extended_Field_1: string
  extended_Field_2: string
  extended_Field_3: string
  extended_Field_4: string
  extended_Field_5: string
  anticoagulant: number
  anticoagulantunit: string
  venous_Pressure_Unit: string
  artery_Pressure: number | null
  crrt_Class: string
}

export interface MedCureDrug {
  CURE_DRUG_ID: string
  CURE_ID: string
  RECIPE_ID: string
  HEMO_ID: string
  DRUG_NAME: string
  DRUG_SPEC: string
  USAGE: string
  DOSE: string
  DOSE_UNIT: string
  EXEC_NURSE: string
  EXEC_TIME: string
  STATE: string
  CREATE_DATE: string
  CREATE_USER: string
  IS_LONG: string
  REMARK: string
  LONG_USAGE: string
}

export interface MedCureLongDrug {
  CURE_DRUG_ID: string
  CURE_ID: string
  RECIPE_ID: string
  HEMO_ID: string
  DRUG_NAME: string
  DRUG_SPEC: string
  USAGE: string
  DOSE: string
  DOSE_UNIT: string
  EXEC_NURSE: string
  EXEC_TIME: string
  STATE: string
  CREATE_DATE: string
  CREATE_USER: string
  IS_LONG: string
  REMARK: string
  LONG_USAGE: string
}

export interface MedRecipeInfo {
  RECIPE_ID: string
  PATIENT_ID: string
  HEMO_ID: string
  RECIPE_DATE: string
  RECIPE_TYPE: string
  RECIPE_STATUS: string
  PURIFICATION_MODE: string
  FREQUENCY_HOURS: number
  FREQUENCY_MINUTE: number
  BLOOW_FLOW: number
  DIALYSATE_FLOW: number
  DIALYSATE_TEMPERATURE: number
  DRY_WEIGHT: number
  HEPARIN_SPECIES: string
  FIRST_HEPARIN: number
  DOSIS_SUSTENTATIVA: number
  MACHINE_TYPE: string
  PURIFIER_NAME: string
  PURIFIER_M2: number
  USE_TYPE: string
  REUSE_TIMES: number
  A_LIQUID: string
  B_LIQUID: string
  BIRCARBONATE: number
  AMYLACEUM: number
  CALCIUM_ION: number
  SODION: number
  POTASSIUM_ION: number
  VASCULAR_ACCESS_ID: string
  VASCULAR_ACCESS_TYPE: string
  PRIMARY_DOCTOR: string
  PRIMARY_NURSE: string
  CREATE_DATE: string
  CREATE_USER: string
  MEMO: string
}

export interface MedNurse {
  NURSE_ID: string
  NURSE_NAME: string
  EMP_NO: string
  DEPT_ID: string
}

export interface MedDoctor {
  DOCTOR_ID: string
  DOCTOR_NAME: string
  EMP_NO: string
  DEPT_ID: string
}

export interface MedVasularAccess {
  VASCULAR_ACCESS_ID: string
  PATIENT_ID: string
  VASCULAR_ACCESS_TYPE: string
  VASCULAR_ACCESS_FIRM: string
  VASCULAR_ACCESS_GLIDE: string
  VASCULAR_ACCESS_SWELLING: string
  VASCULAR_ACCESS_ERRHYISIS: string
  VASCULAR_ACCESS_THROMBUS: string
  VASCULAR_ACCESS_BLOOD: string
  CREATE_DATE: string
}

export interface PatientSearchParam {
  date: string
  timeRangeType: string
  roomId: string
  userId?: string
}

export interface ApiResponse<T> {
  success: boolean
  data: T | null
  message: string
  code?: string
}