# SlideBottomPanel [![SlideBottomPanel](https://img.shields.io/badge/kingideayou-SlideBottomPanel-brightgreen.svg?style=flat)](https://github.com/kingideayou/SlideBottomPanel)
底部划出视图，轻松实现「知乎日报β版」效果（效果见底部效果图）  
可以划出的视图可以包裹 ListView 及 ScrollView。并且 ListView 及 ScrollView 可以在第二级或第三级视图中。  
Demo apk 下载地址：[SlideBottomPanelDemo 下载](https://github.com/kingideayou/SlideBottomPanel/raw/master/apk/SlideBottomPanelDemo.apk)

<img src="https://raw.githubusercontent.com/kingideayou/SlideBottomPanel/master/imgs/SlideBottomPanel.png" width = "120" height = "120" alt="ListView 效果" align=center />  

## 更新日志
*  1.0.3 版本修复了多重嵌套时，隐藏 PanelTitle 时 Panel 出现跳动的 Bug

# How to use 如何使用

###导入项目

##### Gradle  
      compile 'com.github.kingideayou:SlideBottomPanel:1.0.3'
##### Import  

首先下载 SlideBottomPanel，将 SlideBottomPanel 文件夹拷贝到项目的目录下面，然后在setting.gradle文件中增加文件夹名称

      include ":SlideBottomPanel"

然后在我们需要依赖这个模块的module中的build.gradle文件中加入如下代码:
    
      compile project(':SlideBottomPanel')

### 布局文件      
导入成功后，只需要在 XML 文件中添加如下视图（层级比较简单 FrameLayout 的子视图直接包含 ListView 或者 ScrollView）  
下面布局只作简单演示，复杂效果请看 Demo.

      <!-- sbp_hide_panel_title 为滑动过程中及子视图显示是是否需要隐藏标题，默认显示（目前只支持 FrameLayout） -->
      <!-- background_layout 为首页显示在滑块底部的视图（下面的示例图中的 WebView） -->
      <me.next.slidebottompanel.SlideBottomPanel
        android:id="@+id/sbv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:sbp_title_height_no_display="55dp"
        app:sbp_panel_height="380dp"
        app:sbp_hide_panel_title="true"
        app:sbp_background_layout="@layout/background_layout">


        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="380dp"
            android:background="#ffffff"
            android:orientation="vertical">

            <ListView
                android:id="@+id/list_view"
                android:layout_width="match_parent"
                android:layout_height="match_parent"/>
                
            <!-- 标题，如果需要完整显示标题，设置 sbp_title_height_no_display="标题的高度" -->
            <TextView
                android:layout_width="match_parent"
                android:layout_height="55dp"
                text="我是标题"
                android:orientation="horizontal"
                android:gravity="center_vertical"
                android:paddingLeft="@dimen/activity_horizontal_margin"
                android:paddingRight="@dimen/activity_horizontal_margin"
                android:background="#ffffff"/>

        </FrameLayout>

    </me.next.slidebottompanel.SlideBottomPanel>
    
同时也支持复杂一点的效果（知乎日报β版），子视图 FrameLayout 中包含 ViewGroup，ViewGroup 包含 ListView 或 ScrollView
    
    <me.next.slidebottompanel.SlideBottomPanel
        android:id="@+id/sbv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:sbp_hide_panel_title="true"
        app:sbp_title_height_no_display="55dp"
        app:sbp_panel_height="380dp"
        app:sbp_background_layout="@layout/background_layout">
      
      
        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="380dp"
            android:background="#ffffff"
            android:orientation="vertical">

            <LinearLayout
                android:weightSum="9"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="vertical">
                
                <ListView
                    android:id="@+id/list_view"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:layout_weight="8"/>
                
                <TextView
                    android:id="@+id/tv_edit"
                    android:layout_weight="1"
                    android:layout_width="match_parent"
                    android:layout_height="0dp"
                    android:layout_gravity="center_vertical"
                    android:gravity="center_vertical"
                    android:layout_marginLeft="@dimen/activity_horizontal_margin"
                    android:text="写点评..."/>

            </LinearLayout>
        </FrameLayout>
        
        <TextView
            android:layout_width="match_parent"
            android:layout_height="55dp"
            text="我是标题"
            android:orientation="horizontal"
            android:gravity="center_vertical"
            android:paddingLeft="@dimen/activity_horizontal_margin"
            android:paddingRight="@dimen/activity_horizontal_margin"
            android:background="#ffffff"/>

    </me.next.slidebottompanel.SlideBottomPanel>
  
### 代码控制  
完成布局文件后，在 Activity 中将对应的视图填充即可。

    //隐藏 SlideBottomPanel
        if (sbv.isPanelShowing()) {
            sbv.hide();
        }


# 效果图

<img src="https://raw.githubusercontent.com/kingideayou/SlideBottomPanel/master/imgs/demo_1.png" width = "310" height = "560" alt="SlideBottomPanel" align=center />
<img src="https://raw.githubusercontent.com/kingideayou/SlideBottomPanel/master/imgs/demo_2.png" width = "310" height = "560" alt="SlideBottomPanel" align=center />

动图展示:

<img src="https://github.com/kingideayou/SlideBottomPanel/blob/master/imgs/demo_zhihu.gif" width = "380" height = "620" alt="知乎效果" align=center />
<img src="https://github.com/kingideayou/SlideBottomPanel/blob/master/imgs/demo_list_view.gif" width = "380" height = "620" alt="ListView 效果" align=center />  

## 鸣谢
此项目的完成要感谢 [MultiCardMenu](https://github.com/wujingchao/MultiCardMenu)

## License

    Copyright 2015 NeXT

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
