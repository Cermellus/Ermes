import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICreditReferenceType } from 'app/shared/model/credit-reference-type.model';
import { CreditReferenceTypeService } from './credit-reference-type.service';

@Component({
    selector: 'jhi-credit-reference-type-delete-dialog',
    templateUrl: './credit-reference-type-delete-dialog.component.html'
})
export class CreditReferenceTypeDeleteDialogComponent {
    creditReferenceType: ICreditReferenceType;

    constructor(
        protected creditReferenceTypeService: CreditReferenceTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.creditReferenceTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'creditReferenceTypeListModification',
                content: 'Deleted an creditReferenceType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-credit-reference-type-delete-popup',
    template: ''
})
export class CreditReferenceTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ creditReferenceType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CreditReferenceTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.creditReferenceType = creditReferenceType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/credit-reference-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/credit-reference-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
