import { Biere } from '../biere';
import { UserExtra } from '../user-extra';
export class Commentaire {
    constructor(
        public id?: number,
        public note?: number,
        public commentaire?: string,
        public biere?: Biere,
        public user?: UserExtra,
    ) {
    }
}
