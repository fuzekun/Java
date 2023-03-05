function loadXml(fileName){
    //@mrthink.net
    var xmlDoc = null;
    if (window.ActiveXObject) {//写给ie系
        xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
        xmlDoc.async = false;//这句别漏掉,否则IE系会报完成该操作所需的数据还不可用
        //xmlDoc.loadXML(fileName);//这个是用来加载xml字符串的
        xmlDoc.load(fileName);//如果用的是xml文件。
        document.write("加载成功");
    }
    else
    if (document.implementation && document.implementation.createDocument) {//webkit,Geckos,Op内核的
        var xmlhttp = new window.XMLHttpRequest();
        xmlhttp.open("GET", fileName , false);//类型,文件名,是否缓存
        xmlhttp.send(null);
        xmlDoc = xmlhttp.responseXML;
    }
    else {
        xmlDoc = null;
    }
    return xmlDoc;
}
var xmlDoc = loadXml("Students.xml");

function getAll() {
    var tags = ["学号", "姓名", "性别", "专业", "数学", "英语"];        //元素列表

    //获取根元素
    var studentMessage = "";
    root = xmlDoc.getElementsByTagName("学生");

    //循环遍历信息
    for(i = 0; i < root.length; i++){
        for(j = 0; j < tags.length; j++){
            elarray = root[i].getElementsByTagName(tags[j]);      //学生信息节点
            if(elarray.length != 0){
                el = elarray.item(0);
                text = el.childNodes[0].nodeValue;                 //文本节点的信息
                studentMessage += text + " ";
            }
        }
        studentMessage += "<br/>";
    }
    document.getElementById("studentMessage").innerHTML = studentMessage;
}
function getName() {

    //获取全部的姓名
    var studentName = "姓名<br/>";
    studentName = "";
    x = xmlDoc.getElementsByTagName("姓名");
    for(i = 0; i < x.length; i++){
        studentName += x[i].childNodes[0].nodeValue + "<br/>";
    }
    document.getElementById("studentName").innerHTML = studentName;
}

function getAttr() {
    var studentAttribute = "姓名<br/>";
    studentAttribute = "";
    x = xmlDoc.getElementsByTagName("姓名");
    for(i = 0; i < x.length; i++){
        var likeThing = x[i].getAttribute("爱好");
        if(likeThing != null) {
            studentAttribute += x[i].childNodes[0].nodeValue+ "的爱好是:";
            studentAttribute += likeThing + "<br/>";
        }
        //         else studentAttribute += "未得到" + "<br/>";//处理空值的方式
    }
    document.getElementById("studentAttribute").innerHTML = studentAttribute;
}
//处理学生信息
function submitStuMsg(requireType){

    nodeType = ["姓名", "性别", "专业", "学号", "数学", "英语"];            //节点的类型
    studentMessage = new Array();                                          //学生信息数组
    //获取并验证表单信息
    for(i = 0; i < 6; i++){
        content = document.forms['myform'][nodeType[i]].value;
        if((content == null || content == "") && requireType == 'insert'){//插入情况下所有必须需要
            alert(nodeType[i] + "不能为空");
            return ;
        }
        studentMessage[i] = content;
    }
    //进行信息处理
    if(requireType == 'insert') {
        // document.write("插入");
        /*
        * 1.创建根节点
        * 2.创建节点文本
        * 3.创建属性节点
        * 4.创建子节点并且插入根节点，同时将文本节点和属性节点插入子节点
        * 5.将节点加入文档
        * */
        //1.创建根节点
        newnoder = xmlDoc.createElement("学生");
        //2.创建6个文本
        newtext = new Array();
        for (i = 0; i < 6; i++) {
            newtext[i] = xmlDoc.createTextNode(studentMessage[i]);
        }
        //3.创建属性节点
        //  newAttribute = xmlDoc.createAttribute("爱好");
        //  newAttribute.nodeValue = "coding";
        //4.创建6个子节点并插入
        for (i = 0; i < 6; i++) {
            newnode = xmlDoc.createElement(nodeType[i]);        //创建子节点
            newnode.appendChild(newtext[i]);                    //插入文本
            // if(nodeType[i] == '姓名'){                       //插入属性节点
            //     newNode.setAttribute(newAttribute);
            //  }
            newnoder.appendChild(newnode);                      //插入父节点
        }
        //5.将节点加入文档
        x = xmlDoc.getElementsByTagName("学生名单")[0];
        x.appendChild(newnoder);
        getAll();
        getName();
    }else if(requireType == 'find'){
        /*
        * 1.遍历学生信息，找到匹配的即可
        * */
        //获取根元素
        var studentMessagerel = "";                                      //实际的学生信息
        root = xmlDoc.getElementsByTagName("学生");
        flag = -1;                                                       //学生的位置;
        for(i = 0; i < root.length; i++){
            for(j = 0; j < nodeType.length; j++){
                elarray = root[i].getElementsByTagName(nodeType[j]);    //学生信息节点
                if(elarray.length != 0){
                    el = elarray.item(0);
                    text = el.childNodes[0].nodeValue;                  //文本节点的信息
                    if(studentMessage[j] == text){
                        flag = i;
                        break;
                    }
                }
            }
        }
        if(flag == -1){
            alert("没有这个人!");
            return ;
        }
        //获取这个学生的信息
        for(j = 0; j < nodeType.length; j++) {
            elarray = root[flag].getElementsByTagName(nodeType[j]);   //学生信息节点
            if (elarray.length != 0) {
                el = elarray.item(0);
                text = el.childNodes[0].nodeValue;                 //文本节点的信息
                if(studentMessage[j] != null && studentMessage[j] != ""){
                    if(studentMessage[j] != text) {
                        alert(nodeType[j] + ":"+
                            studentMessage[j] + " " + text + " 不相等," + "信息输入错误,无法删除");
                        return ;
                    }
                }
                studentMessagerel += nodeType[j] + ":" + text + "\n";
            }
        }
        alert("学生的信息是\n" + studentMessagerel);
    }else{
        var studentMessagerel = "";                                      //实际的学生信息
        root = xmlDoc.getElementsByTagName("学生");
        flag = -1;                                                       //学生的位置;
        for(i = 0; i < root.length; i++){
            for(j = 0; j < nodeType.length; j++){
                elarray = root[i].getElementsByTagName(nodeType[j]);    //学生信息节点
                if(elarray.length != 0){
                    el = elarray.item(0);
                    text = el.childNodes[0].nodeValue;                  //文本节点的信息
                    if(studentMessage[j] == text){
                        flag = i;
                        break;
                    }
                }
            }
        }
        if(flag == -1){
            alert("没这个人！");
            return ;
        }
        //查看信息输入是否正确
        for(j = 0; j < nodeType.length; j++) {
            elarray = root[flag].getElementsByTagName(nodeType[j]);   //学生信息节点
            if (elarray.length != 0) {
                el = elarray.item(0);
                text = el.childNodes[0].nodeValue;                 //文本节点的信息
                if (studentMessage[j] != null && studentMessage[j] != "") {
                    if (studentMessage[j] != text) {
                        alert(nodeType[j] + ":"+
                            studentMessage[j] + " " + text + " 不相等," + "信息输入错误,无法删除");
                        return;
                    }
                }
            }
        }
        //直接一句话
        x = xmlDoc.getElementsByTagName("学生")[flag];
        xmlDoc.documentElement.removeChild(x);
        getAll();
        getName();
    }
}