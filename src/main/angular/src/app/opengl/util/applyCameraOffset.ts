export const applyCameraOffset = (points: Array<{ x: number, y: number }>,
                                  camera: { x: number, y: number, scale: number }) => {
    return points.map(value => {
        return {
            x: (value.x + camera.x) * camera.scale,
            y: (value.y + camera.y) * camera.scale
        }
    });
}