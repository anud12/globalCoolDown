export const normalizeVector = (coordinates) => {
    const length = Math.sqrt(coordinates.reduce((sum, value) => {
        sum += Math.pow(value, 2);
        return sum
    }, 0));
    if(length === 0) {
        return coordinates;
    }
    return coordinates.map(value => value / length);
}