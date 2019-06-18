export const get = (path) => {
    return new Promise((resolve, reject) => {
        let request = new XMLHttpRequest();
        request.open("get", path);
        request.onload = () => {
            console.log(path , "\n", request.responseText);
            resolve(request.responseText)
        };
        request.send();
    })
};