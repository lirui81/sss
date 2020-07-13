/**
 * 获取根路径
 * @returns
 */
function getRootPath(){
    var curPageUrl = window.document.location.href;
    var rootPath = curPageUrl.split("//")[0] + "//" + curPageUrl.split("//")[1].split("/")[0];
    return rootPath;
}