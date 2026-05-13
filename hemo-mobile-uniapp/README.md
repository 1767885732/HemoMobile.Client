# 血透移动系统 - UniApp 版本

## 项目简介

这是一个使用 UniApp 框架开发的血透移动系统，支持多平台运行（H5、微信小程序、Android、iOS）。

## 项目结构

```
hemo-mobile-uniapp/
├── src/                    # 源代码
│   ├── pages/             # 页面文件
│   ├── components/        # 组件
│   ├── stores/            # 状态管理
│   ├── styles/            # 样式文件
│   ├── utils/             # 工具函数
│   ├── App.vue            # 应用入口
│   ├── main.ts            # 主入口文件
│   ├── manifest.json      # 应用配置
│   └── pages.json         # 页面配置
├── dist/                  # 构建产物
│   └── build/
│       ├── h5/            # H5 版本
│       └── mp-weixin/     # 微信小程序版本
├── package.json           # 项目配置
├── vite.config.ts         # Vite 配置
└── tsconfig.json          # TypeScript 配置
```

## 功能模块

| 页面 | 功能描述 |
|------|----------|
| 登录页面 | 用户登录、IP配置 |
| 设置页面 | 服务器地址设置 |
| 血透信息搜索 | 日期、时段、透析室筛选 |
| 患者列表 | 患者信息展示、状态管理 |
| 患者详情 | 透析信息、评估与治疗情况 |
| 临时医嘱 | 医嘱查看与执行 |
| 透析参数 | 参数录入 |
| 透析小结 | 小结编辑 |

## 技术栈

- **框架**: UniApp + Vue 3
- **语言**: TypeScript
- **状态管理**: Pinia
- **样式**: SCSS
- **构建工具**: Vite

## 安装与运行

```bash
# 安装依赖
npm install

# 开发模式
npm run dev:h5

# 构建 H5 版本
npm run build:h5

# 构建微信小程序
npm run build:mp-weixin

# 构建 App 版本
npm run build:app-plus
```

## 运行测试

H5 版本已构建完成，可通过以下方式访问：

```bash
cd dist/build/h5
python3 -m http.server 8080
# 访问 http://localhost:8080
```

## 平台支持

| 平台 | 状态 | 说明 |
|------|------|------|
| H5 | ✅ 已完成 | Web 端可直接运行 |
| 微信小程序 | ✅ 已完成 | 需导入微信开发者工具 |
| Android | ⚠️ 待配置 | 需使用 HBuilderX 云打包 |
| iOS | ⚠️ 待配置 | 需使用 HBuilderX 云打包 |

## 设计特点

- **主色调**: 青绿色 (#4DB6AC)，符合医疗应用风格
- **响应式设计**: 适配平板和手机
- **统一交互**: 底部导航支持快捷切换

## 开发者

上海普陀区人民医院血透中心

## 许可证

私有项目
