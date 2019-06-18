export const facesToVertexAndNormalArrays = (faces, vertices, normals) => {
    return {
        vertices: faces.reduce((array, currentValue) => {
            vertices[currentValue.vertex].forEach(value => {
                array.push(value)
            });
            return array;
        }, []),
        normals: faces.reduce((array, currentValue) => {
            normals[currentValue.normal].forEach(value => {
                array.push(value)
            });
            return array;
        }, []),
        indices: faces.reduce((array, currentValue, index) => {
            array.push(index);
            return array;
        }, []),
        colors: faces.reduce((array, currentValue, index) => {
            array.push(1);
            array.push(1);
            array.push(1);
            return array;
        }, []),
    }

}