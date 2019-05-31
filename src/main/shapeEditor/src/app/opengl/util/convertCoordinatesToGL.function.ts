export const convertCoordinatesToGL = (coordinates: { x: number, y: number }, domRect: ClientRect) => {
    const x = (((coordinates.x - domRect.width / 2) / (domRect.width / 2)));
    const y = ((domRect.height / 2 - coordinates.y) / (domRect.height / 2));
    return {x, y};
};
