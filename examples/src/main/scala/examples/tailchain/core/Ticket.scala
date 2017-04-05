package examples.tailchain.core

import com.google.common.primitives.Ints
import scorex.core.serialization.Serializer
import scorex.crypto.hash.Blake2b256

import scala.util.Try

case class PartialProof(id: Array[Byte], rootHash: Array[Byte], proof: Array[Byte]) extends Serializer[PartialProof] {

  import PartialProof._

  override def toBytes(obj: PartialProof): Array[Byte] = {
    val proofSize = Ints.toByteArray(proof.length)
    id ++ rootHash ++ proofSize ++ proof
  }

  override def parseBytes(bytes: Array[Byte]): Try[PartialProof] = Try {
    require(bytes.length < HardLimit)
    val id = bytes.take(IdSize)
    val root = bytes.slice(IdSize, IdSize + RootSize)
    val proofLength = Ints.fromByteArray(bytes.slice(IdSize + RootSize, IdSize + RootSize + 4))
    val proof = bytes.takeRight(proofLength)
    PartialProof(id, root, proof)
  }
}

object PartialProof {
  val HardLimit = 5000 // we assume that serialized partial proof is no more than 5K, more than enough for any imaginable case

  val IdSize = Blake2b256.DigestSize
  val RootSize = Blake2b256.DigestSize
}

case class Ticket(minerKey: Array[Byte], nonce: Array[Byte], partialProofs: IndexedSeq[PartialProof])
