import { useState } from "react";
import API from "../api/imageApi";

function LayerImagePanel({
  setProcessedPreview
}) {

  const [backgroundImage,
    setBackgroundImage] =
    useState(null);

  const [overlayImage,
    setOverlayImage] =
    useState(null);

  const [positionX,
    setPositionX] =
    useState(0);

  const [positionY,
    setPositionY] =
    useState(0);

  const handleLayerImages =
    async () => {

      if (
        !backgroundImage ||
        !overlayImage
      ) {
        alert(
          "Upload both images"
        );
        return;
      }

      try {

        const formData =
          new FormData();

        formData.append(
          "backgroundImage",
          backgroundImage
        );

        formData.append(
          "overlayImage",
          overlayImage
        );

        formData.append(
          "positionX",
          positionX
        );

        formData.append(
          "positionY",
          positionY
        );

        const response =
          await API.post(
            "/layer",
            formData,
            {
              responseType: "blob"
            }
          );

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
          "Layering failed"
        );
      }
    };

  return (
    <div className="layer-panel">

      <h2>
        Image Layering
      </h2>

      <input
        type="file"
        accept="image/*"
        onChange={(e) =>
          setBackgroundImage(
            e.target.files[0]
          )
        }
      />

      <input
        type="file"
        accept="image/*"
        onChange={(e) =>
          setOverlayImage(
            e.target.files[0]
          )
        }
      />

      <input
        type="number"
        placeholder="Position X"
        value={positionX}
        onChange={(e) =>
          setPositionX(
            e.target.value
          )
        }
      />

      <input
        type="number"
        placeholder="Position Y"
        value={positionY}
        onChange={(e) =>
          setPositionY(
            e.target.value
          )
        }
      />

      <button
        onClick={
          handleLayerImages
        }
      >
        Layer Images
      </button>

    </div>
  );
}

export default LayerImagePanel;