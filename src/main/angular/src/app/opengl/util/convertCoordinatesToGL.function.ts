export const convertCoordinatesToGL = (coordinates: { x: number, y: number }, domRect: ClientRect) => {
  const x = (coordinates.x - domRect.height / 2) / (domRect.height / 2);
  const y = (domRect.width / 2 - coordinates.y) / (domRect.width / 2);
  return {x, y};
};
