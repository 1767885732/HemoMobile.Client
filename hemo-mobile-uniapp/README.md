# 血透移动系统 - UniApp 版本

## 项目简介

这是一个使用 UniApp 框架开发的血透移动系统，完全重写了原 Android 原生应用，包含完整的后端接口调用。

## 与原 Android 端的对应关系

### API 接口对照表

| 功能模块 | 原 Android 接口 | UniApp API | 状态 |
|---------|----------------|-----------|------|
| 登录 | `/api/MedUser` | `api.user.login()` | ✅ |
| 获取患者列表 | `/api/Schedule/GetPatientScheduleList` | `api.schedule.getPatientScheduleList()` | ✅ |
| 获取患者详情 | `/api/Schedule/GetPatientScheduleByDateAndHemoId` | `api.schedule.getPatientScheduleByDateAndHemoId()` | ✅ |
| 开始治疗 | `/api/Schedule/StartCure` | `api.schedule.startCure()` | ✅ |
| 结束治疗 | `/api/Schedule/SavePatientSchedule` | `api.schedule.savePatientSchedule()` | ✅ |
| 获取治疗信息 | `/Api/Cure/GetMainCureByRecipeId` | `api.cure.getMainCureByRecipeId()` | ✅ |
| 保存治疗信息 | `/Api/Cure/SaveCureMain` | `api.cure.saveCureMain()` | ✅ |
| 获取处方信息 | `/Api/Recipe/GetRecipeByRecipeId` | `api.recipe.getRecipeByRecipeId()` | ✅ |
| 获取长期医嘱 | `/Api/CureDrug/GetLongCureDrugByHemoID` | `api.drug.getLongOrders()` | ✅ |
| 获取临时医嘱 | `/Api/CureDrug/GetValidCureDrugByHemoId` | `api.drug.getTempOrders()` | ✅ |
| 执行医嘱 | `/Api/CureDrug/UpdateCureDrugStateByParma` | `api.drug.updateCureDrugState()` | ✅ |
| 获取透析参数 | `/Api/MedHemodialysisParameters/GetHemoParametersByCureID` | `api.parameter.getHemoParametersByCureId()` | ✅ |
| 保存透析参数 | `/Api/MedHemodialysisParameters/SaveHemoParameters` | `api.parameter.saveHemoParameter()` | ✅ |
| 删除透析参数 | `/Api/MedHemodialysisParameters/DeleteHemoParametersById` | `api.parameter.deleteHemoParameter()` | ✅ |
| 上传患者照片 | `/Api/MedPatient/UploadPatientPhoto` | `api.patient.uploadPhoto()` | ✅ |

### 数据模型对照表

| Android 实体类 | UniApp TypeScript 接口 | 状态 |
|---------------|----------------------|------|
| MedUser | `MedUser` | ✅ |
| MedPatientSchedule | `MedPatientSchedule` | ✅ |
| MedCureInfo | `MedCureInfo` | ✅ |
| MedHemoParameter | `MedHemoParameter` | ✅ |
| MedCureDrug | `MedCureDrug` | ✅ |
| MedCureLongDrug | `MedCureLongDrug` | ✅ |
| MedRecipeInfo | `MedRecipeInfo` | ✅ |
| MedNurse | `MedNurse` | ✅ |
| MedDoctor | `MedDoctor` | ✅ |
| MedVasularAccess | `MedVasularAccess` | ✅ |

## 项目结构

```
hemo-mobile-uniapp/
├── src/
│   ├── pages/              # 页面文件
│   │   ├── login/         # 登录页面
│   │   ├── settings/      # 设置页面（IP配置）
│   │   ├── search/        # 血透信息搜索
│   │   ├── patient-list/  # 患者列表
│   │   ├── patient-detail/# 患者详情
│   │   ├── orders/        # 临时医嘱
│   │   ├── dialysis-param/# 透析参数
│   │   └── summary/       # 透析小结
│   ├── components/        # 组件
│   ├── stores/            # Pinia 状态管理
│   │   └── app.ts        # 主状态管理（集成所有API调用）
│   ├── styles/            # 样式文件
│   │   ├── variables.scss # SCSS 变量
│   │   └── index.scss    # 全局样式
│   └── utils/            # 工具函数
│       ├── api.ts        # API 服务层（与原Android接口一致）
│       └── types.ts      # TypeScript 类型定义
├── dist/                  # 构建产物
└── package.json          # 项目配置
```

## 技术栈

- **框架**: UniApp + Vue 3
- **语言**: TypeScript
- **状态管理**: Pinia
- **样式**: SCSS
- **构建工具**: Vite
- **HTTP请求**: uni.request

## 本地运行

### 方式一：使用 HBuilderX（推荐）

1. 下载安装 [HBuilderX](https://www.dcloud.io/hbuilderx.html)
2. 打开 HBuilderX
3. 文件 -> 导入 -> 从本地目录导入
4. 选择 `hemo-mobile-uniapp` 文件夹
5. 运行 -> 运行到浏览器 -> Chrome

### 方式二：使用命令行

```bash
# 安装依赖
cd hemo-mobile-uniapp
npm install

# H5 开发模式
npm run dev:h5

# H5 构建
npm run build:h5

# 微信小程序构建
npm run build:mp-weixin

# App 构建（需要HBuilderX）
npm run build:app-plus
```

## 功能模块

### 1. 登录模块
- 用户名密码登录
- IP 地址配置
- Token 存储和自动登录

### 2. 患者管理模块
- 日期、时段、透析室筛选
- 患者列表展示
- 患者状态管理（未开始/治疗中/已完成）
- 开始治疗/结束治疗

### 3. 治疗信息模块
- 透析信息编辑
- 评估与治疗情况
- 责任医生/护士管理

### 4. 医嘱管理模块
- 长期医嘱查看
- 临时医嘱执行
- 医嘱状态更新

### 5. 透析参数模块
- 参数列表展示
- 参数录入（静脉压、跨膜压、血流量等）
- 参数删除

### 6. 透析小结模块
- 小结内容编辑
- 模板快速填写

## 接口配置

默认 API 地址：`http://192.168.100.66:8014`

可在设置页面修改 API 地址，修改后会保存到本地存储。

## 数据流程

```
用户操作 → Store (app.ts) → API 层 (api.ts) → 后端服务器
                ↓
         本地状态更新
                ↓
         页面响应式更新
```

## 错误处理

- 所有 API 调用都包含 try-catch 错误处理
- 网络请求显示加载提示
- 失败时显示错误信息
- 服务器未连接时使用模拟数据

## 开发者

上海普陀区人民医院血透中心

## 许可证

私有项目
