export const vertexListToArray = (vertexList) => {
    const array = [];
    vertexList.map(value => {
        array.push(value.x);
        array.push(value.y);
        array.push(value.z);
        array.push(1);
    });
    return array;
}