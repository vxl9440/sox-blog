let E = window.wangEditor
let editor = new E('#tool-bar', '#text-bar')  // 两个参数也可以传入 elem 对象，class 选择器
editor.customConfig.zIndex = 100
editor.customConfig.menus = [
    'head',  // 标题
    'bold',  // 粗体
    'fontSize',  // 字号
    'italic',  // 斜体
    'underline',  // 下划线
    'strikeThrough',  // 删除线
    'foreColor',  // 文字颜色
    'link',  // 插入链接
    'list',  // 列表
    'justify',  // 对齐方式
    'image',  // 插入图片
    'table',  // 表格
    'code',  // 插入代码
    'undo',  // 撤销
    'redo'  // 重复
]


editor.customConfig.lang = {
    '设置标题': 'title',
    '正文': 'p',
    '链接文字': 'link text',
    '链接': 'link',
    '上传图片': 'upload image',
    '上传': 'upload',
    '创建': 'init',
    '字号':'font size',
    '文字颜色':'font color',
    '背景色':'background color',
    '设置列表':'set list',
    '对齐方式':'justify',
    '插入表格':'insert table',
    // 还可自定添加更多
}
editor.customConfig.uploadImgShowBase64 = true; //update to server
editor.customConfig.showLinkImg = false;
editor.create();