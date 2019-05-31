export const convertCoordinatesToRelative = (coordinates: { x: number, y: number }, domRect: ClientRect) => {
  const x = coordinates.x - domRect.left;
  const y = coordinates.y - domRect.top;
  return {x, y};
}
