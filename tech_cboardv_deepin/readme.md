1. 修改路由配置文件router/index.js
2. 修改网络api配置文件，util/https/api.js文件
3. 新编写Hello.vue页面，引用组件显示图标。
4. 修改components/widgets/ChartContent.vue文件，加入第381～384行，加入对面积折线图等的支持。
5. 在每一个组件的vue文件中，可以修改Dashboard默认的显示高度。例如，components/dashboard/widgets/ChartContent.vue文件中，第111行代码，就是用来指定默认高度的。
6. 在后台配置看板的时候，可以添加列的时候给出制定高度，以及制定宽度，用1到12表示份数（可以指定宽度的份数）。
7. 堆叠线、面积折线默认CBoard-V是不支持的，通过修改源码加入支持。修改components/widgets/ChartContent.vue文件，加入第381～384行代码。代码片段如下：
```java
let seriesItem = {
    type: values[j].series_type,
     name: name,
     barMaxWidth: 40,
     data: []
};
//加入代码，处理堆叠线和面积折线支持。   添加人：蝈蝈<guozhanxian@gmail.com>
if (values[j].series_type.indexOf('arealine') !== -1 || values[j].series_type.indexOf('stackline') !== -1) {
  seriesItem.type = 'line';
  seriesItem.areaStyle = {};
}
//根据 option 设置 bar 的样式
if (styleOption.bar) {
 if (styleOption.bar.width !== '') seriesItem.barWidth = styleOption.bar.width;
 if (styleOption.bar.maxWidth !== '') seriesItem.barMaxWidth = styleOption.bar.maxWidth;
 if (styleOption.bar.minHeight !== '') seriesItem.barMinHeight = styleOption.bar.minHeight;
}
```
