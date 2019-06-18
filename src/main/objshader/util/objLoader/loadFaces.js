function buildFace(face) {
    const string = face.split("/");
    return {
        vertex: parseInt(string[0]) - 1,
        normal: parseInt(string[2]) - 1
    }
}

export const loadFaces = (file) => {
    return file
    .split("\n")
    .reduce((previousValue, currentValue) => {
        const line = currentValue.split(" ");
        if (line[0] === "f") {
            previousValue.push(buildFace(line[1]));
            previousValue.push(buildFace(line[2]));
            previousValue.push(buildFace(line[3]));
        }
        return previousValue;
    }, []);
}