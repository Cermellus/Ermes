import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICreditReturnType } from 'app/shared/model/credit-return-type.model';
import { CreditReturnTypeService } from './credit-return-type.service';

@Component({
    selector: 'jhi-credit-return-type-delete-dialog',
    templateUrl: './credit-return-type-delete-dialog.component.html'
})
export class CreditReturnTypeDeleteDialogComponent {
    creditReturnType: ICreditReturnType;

    constructor(
        protected creditReturnTypeService: CreditReturnTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.creditReturnTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'creditReturnTypeListModification',
                content: 'Deleted an creditReturnType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-credit-return-type-delete-popup',
    template: ''
})
export class CreditReturnTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ creditReturnType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CreditReturnTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.creditReturnType = creditReturnType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/credit-return-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/credit-return-type', { outlets: { popup: null } }]);
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
