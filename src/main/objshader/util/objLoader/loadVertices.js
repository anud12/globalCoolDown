export const loadVertices = (file) => {

    return file
    .split("\n")
    .reduce((previousValue, currentValue) => {
        const line = currentValue.split(" ");
        if (line[0] === "v") {
            previousValue.push([
                line[1] ? line[1] : 0,
                line[2] ? line[2] : 0,
                line[3] ? line[3] : 0
            ]);
        }
        return previousValue;
    }, [])

}