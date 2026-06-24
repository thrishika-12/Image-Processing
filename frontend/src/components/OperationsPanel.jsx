import { useState } from "react";
import API from "../api/imageApi";

function OperationsPanel({
  imageFile,
  setProcessedPreview
}) {

  const [operation, setOperation] =
    useState("brightness");

  const [parameter, setParameter] =
    useState(50);

  const [direction, setDirection] =
    useState("clockwise");

  const [flipType, setFlipType] =
    useState("horizontal");

  const [shapeResult, setShapeResult] =
    useState("");

  const processImage = async () => {

    if (!imageFile) {
      alert("Upload an image first");
      return;
    }

    try {

      const formData = new FormData();

      let response;
      let endpoint = "";

      switch (operation) {

        case "brightness":

          endpoint = "/brightness";

          formData.append("image", imageFile);
          formData.append("brightness", parameter);

          response = await API.post(
            endpoint,
            formData,
            {
              responseType: "blob"
            }
          );

          break;

        case "contrast":

          endpoint = "/contrast";

          formData.append("image", imageFile);
          formData.append(
            "contrastFactor",
            parameter
          );

          response = await API.post(
            endpoint,
            formData,
            {
              responseType: "blob"
            }
          );

          break;

        case "blur":

          endpoint = "/blur";

          formData.append("image", imageFile);
          formData.append(
            "intensity",
            parameter
          );

          response = await API.post(
            endpoint,
            formData,
            {
              responseType: "blob"
            }
          );

          break;

        case "sharpen":

          endpoint = "/sharpen";

          formData.append("image", imageFile);
          formData.append(
            "intensity",
            parameter
          );

          response = await API.post(
            endpoint,
            formData,
            {
              responseType: "blob"
            }
          );

          break;

        case "rotate":

          endpoint = "/rotate";

          formData.append("image", imageFile);
          formData.append("angle", parameter);
          formData.append(
            "direction",
            direction
          );

          response = await API.post(
            endpoint,
            formData,
            {
              responseType: "blob"
            }
          );

          break;

        case "flip":

          endpoint = "/flip";

          formData.append("image", imageFile);
          formData.append(
            "flipType",
            flipType
          );

          response = await API.post(
            endpoint,
            formData,
            {
              responseType: "blob"
            }
          );

          break;

        case "grayscale":

          endpoint = "/grayscale";

          formData.append("image", imageFile);

          response = await API.post(
            endpoint,
            formData,
            {
              responseType: "blob"
            }
          );

          break;

        case "background":

          endpoint =
            "/remove-background";

          formData.append("image", imageFile);
          formData.append(
            "threshold",
            parameter
          );

          response = await API.post(
            endpoint,
            formData,
            {
              responseType: "blob"
            }
          );

          break;

        case "zoom":

          endpoint = "/zoom";

          formData.append("image", imageFile);
          formData.append(
            "zoomFactor",
            parameter
          );

          response = await API.post(
            endpoint,
            formData,
            {
              responseType: "blob"
            }
          );

          break;

        case "zoomout":

          endpoint = "/zoom-out";

          formData.append("image", imageFile);
          formData.append(
            "zoomFactor",
            parameter
          );

          response = await API.post(
            endpoint,
            formData,
            {
              responseType: "blob"
            }
          );

          break;

        case "shape":

          endpoint = "/detect-shape";

          formData.append("image", imageFile);

          const shapeResponse =
            await API.post(
              endpoint,
              formData
            );

          setShapeResult(
            shapeResponse.data.shape
          );

          return;

        default:
          return;
      }

      const imageUrl =
        URL.createObjectURL(
          response.data
        );

      setProcessedPreview(
        imageUrl
      );

    } catch (error) {

      console.error(error);

      alert(
        "Processing failed"
      );
    }
  };

  return (
    <div className="operations-panel">

      <h2>Operations</h2>

      <select
        value={operation}
        onChange={(e) =>
          setOperation(
            e.target.value
          )
        }
      >

        <option value="brightness">
          Brightness
        </option>

        <option value="contrast">
          Contrast
        </option>

        <option value="blur">
          Blur
        </option>

        <option value="sharpen">
          Sharpen
        </option>

        <option value="rotate">
          Rotate
        </option>

        <option value="flip">
          Flip
        </option>

        <option value="grayscale">
          Grayscale
        </option>

        <option value="background">
          Remove Background
        </option>

        <option value="zoom">
          Zoom In
        </option>

        <option value="zoomout">
          Zoom Out
        </option>

        <option value="shape">
          Shape Detection
        </option>

      </select>

      {operation !== "grayscale" &&
       operation !== "shape" &&
       operation !== "flip" && (

        <input
          type="number"
          value={parameter}
          onChange={(e) =>
            setParameter(
              e.target.value
            )
          }
        />

      )}

      {operation === "rotate" && (

        <select
          value={direction}
          onChange={(e) =>
            setDirection(
              e.target.value
            )
          }
        >
          <option value="clockwise">
            Clockwise
          </option>

          <option value="anticlockwise">
            Anti Clockwise
          </option>

        </select>

      )}

      {operation === "flip" && (

        <select
          value={flipType}
          onChange={(e) =>
            setFlipType(
              e.target.value
            )
          }
        >
          <option value="horizontal">
            Horizontal
          </option>

          <option value="vertical">
            Vertical
          </option>

        </select>

      )}

      <button
        onClick={processImage}
      >
        Apply
      </button>

      {shapeResult && (

        <div>

          <h3>
            Detected Shape:
          </h3>

          <p>
            {shapeResult}
          </p>

        </div>

      )}

    </div>
  );
}

export default OperationsPanel;