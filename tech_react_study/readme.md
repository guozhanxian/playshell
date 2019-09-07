# React的特点
- `声明式设计`，React采用声明范式，可以轻松描述应用。
- `高效`，React通过对DOM的模拟，最大限度地减少与DOM的交互。
- `灵活`，React可以与已知的库或框架很好地配合。
- `JSX`，JSX 是 JavaScript 语法的扩展。React 开发不一定使用 JSX ，但我们建议使用它。
- `组件`，通过 React 构建组件，使得代码更加容易得到复用，能够很好的应用在大项目的开发中。
- `单向响应的数据流`，React 实现了单向响应的数据流，从而减少了重复代码，这也是它为什么比传统数据绑定更简单。

# 第一个React例子
```javascript
<div id="example"></div>
<script type="text/babel">
  ReactDOM.render(
    <h1>Hello, world!</h1>,
    document.getElementById('example')
  );
</script>
```

# 安装React
## 页面直接通过CDN导入JS文件
```javascript
<script src="https://cdn.staticfile.org/react/16.4.0/umd/react.development.js"></script>
<script src="https://cdn.staticfile.org/react-dom/16.4.0/umd/react-dom.development.js"></script>
<!-- 生产环境中不建议使用 -->
<script src="https://cdn.staticfile.org/babel-standalone/6.26.0/babel.min.js"></script>
```
- react.min.js，React 的核心库
- react-dom.min.js，提供与 DOM 相关的功能
- babel.min.js，Babel 可以将 ES6 代码转为 ES5 代码，这样我们就能在目前不支持 ES6 浏览器上执行 React 代码。Babel 内嵌了对 JSX 的支持。通过将 Babel 和 babel-sublime 包（package）一同使用可以让源码的语法渲染上升到一个全新的水平。

## 通过npm或者yarn安装React
```
cnpm install -g create-react-app
或者
yarn add -g create-react-app
```

## 使用`create-react-app`创建react脚手架
```
create-react-app XXX
# 启动项目
yarn start 或 npm start
```

# React元素渲染
## 将元素渲染到DOM中
- 有一个HTML元素
```
<div id="example"></div>
```
- 使用`React.render()`方法将内容渲染到HTML中
```javascript
const element = <h1>Hello, world!</h1>;
ReactDOM.render(
    element,
    document.getElementById('example')
);
```

# React组件开发
## 使用JavaScript函数制作组件
```javascript
import React from 'react';
import logo from './logo.svg';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
```

## 使用`ES6`的class制作组件
```javascript
import React from 'react'

export default class Todolist extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            list: ['学习React', '学习英语', '学习Vue']
        };
    }

    handleBtnClick() {
        this.setState({ list: [...this.state.list, "HelloWorld"] });
    }

    render() {
        return (<div>
            <div>
                <input type="text" />
                <button onClick={this.handleBtnClick.bind(this)}>Add</button>
            </div>
            <ul>
                {
                    this.state.list.map((item, index) => {
                        return <li key={index}>{item}</li>;
                    })
                }
            </ul>
        </div>);
    }
}
```

## React组件生命周期的方法
- `componentDidMount`和`componentWillUnmount`被称为生命周期的钩子。
```javascript
class Clock extends React.Component {
  // 构造方法
  constructor(props) {
    super(props);
    this.state = {date: new Date()};
  }
  // 生命周期的钩子，在组件加载时运行
  componentDidMount() {
    this.timerID = setInterval(
      () => this.tick(),
      1000
    );
  }
  // 生命周期的钩子，在组件退出时运行
  componentWillUnmount() {
    clearInterval(this.timerID);
  }
 
  tick() {
    this.setState({
      date: new Date()
    });
  }
 
  render() {
    return (
      <div>
        <h1>Hello, world!</h1>
        <h2>现在是 {this.state.date.toLocaleTimeString()}.</h2>
      </div>
    );
  }
}
 
ReactDOM.render(
  <Clock />,
  document.getElementById('example')
);
```

## State和Props
```javascript
class WebSite extends React.Component {
  constructor() {
      super();
 
      this.state = {
        name: "菜鸟教程",
        site: "https://www.runoob.com"
      }
    }
  render() {
    return (
      <div>
        <Name name={this.state.name} />
        <Link site={this.state.site} />
      </div>
    );
  }
}
 
class Name extends React.Component {
  render() {
    return (
      <h1>{this.props.name}</h1>
    );
  }
}
 
class Link extends React.Component {
  render() {
    return (
      <a href={this.props.site}>
        {this.props.site}
      </a>
    );
  }
}
 
ReactDOM.render(
  <WebSite />,
  document.getElementById('example')
);
```

# React事件处理
## 绑定事件处理函数
```javascript
class Toggle extends React.Component {
  constructor(props) {
    super(props);
    this.state = {isToggleOn: true};
 
    // 这边绑定是必要的，这样 `this` 才能在回调函数中使用
    this.handleClick = this.handleClick.bind(this);
  }
 
  handleClick() {
    this.setState(prevState => ({
      isToggleOn: !prevState.isToggleOn
    }));
  }
 
  render() {
    return (
      <button onClick={this.handleClick}>
        {this.state.isToggleOn ? 'ON' : 'OFF'}
      </button>
    );
  }
}
 
ReactDOM.render(
  <Toggle />,
  document.getElementById('example')
);
```

## 向事件处理函数传递参数
```javascript
class Popper extends React.Component{
    constructor(){
        super();
        this.state = {name:'Hello world!'};
    }
    
    preventPop(name, e){    //事件对象e要放在最后
        e.preventDefault();
        alert(name);
    }
    
    render(){
        return (
            <div>
                <p>hello</p>
                {/* 通过 bind() 方法传递参数。 */}
                <a href="https://reactjs.org" onClick={this.preventPop.bind(this,this.state.name)}>Click</a>
            </div>
        );
    }
}
```