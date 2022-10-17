var delete_ = function (alink) {
    if (confirm("确认删除？")) {
        alink.click();
    }
}

var beforeSubmit = function () {
    let unit = document.getElementsByName("unit");
    let expirationday = document.getElementById("expiration_day").value;
    let resEl = document.getElementById("res");
    let res = 0;
    let radioValue = "";
    for (let i = 0; i < unit.length; i++) {
        if (unit[i].checked) {
            radioValue = unit[i].value
        }
    }
    if (radioValue === "month") {
        res = expirationday * 30;
    } else if (radioValue === "year") {
        res = expirationday * 365;
    } else if (radioValue === "day") {
        res = expirationday;
    }
    resEl.value = res;
    // document.getElementById("goodsMsg").submit();
}

var updateImgName = function (labelEl, inputEl) {
    labelEl.innerText = "文件名：" + inputEl.files[0].name
}