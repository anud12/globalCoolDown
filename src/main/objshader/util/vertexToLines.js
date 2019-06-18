export const vertexToLines = (vertexList) => {
    let array = [];
    let previous = undefined;
    vertexList.map(value => {
        if (previous === undefined) {
            array.push(value);
            return;
        }
        array.push(previous);
        array.push(value);
        previous = value;
        return;
    })
    return array;
}