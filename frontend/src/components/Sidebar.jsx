import OperationControls from "./OperationControls";

const operations = [
  "brightness",
  "contrast",
  "blur",
  "sharpen",
  "rotate",
  "flip",
  "grayscale",
  "background",
  "zoom",
  "zoomout",
  "shape",
  "layer"
];

function Sidebar({
  selectedOperation,
  setSelectedOperation,
  imageFile,
  setProcessedPreview,
  setShapeResult
}) {
  return (
    <div className="sidebar">

      <h2>Operations</h2>

      {operations.map((operation) => (
        <button
          key={operation}
          className={
            selectedOperation === operation
              ? "sidebar-btn active"
              : "sidebar-btn"
          }
          onClick={() =>
            setSelectedOperation(operation)
          }
        >
          {operation
            .replace("zoomout", "Zoom Out")
            .replace("background", "Remove Background")
            .replace("brightness", "Brightness")
            .replace("contrast", "Contrast")
            .replace("blur", "Blur")
            .replace("sharpen", "Sharpen")
            .replace("rotate", "Rotate")
            .replace("flip", "Flip")
            .replace("grayscale", "Grayscale")
            .replace("zoom", "Zoom In")
            .replace("shape", "Shape Detection")
            .replace("layer", "Image Layering")}
        </button>
      ))}

      <OperationControls
        selectedOperation={selectedOperation}
        imageFile={imageFile}
        setProcessedPreview={setProcessedPreview}
        setShapeResult={setShapeResult}
      />

    </div>
  );
}

export default Sidebar;